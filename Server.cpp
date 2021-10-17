#include "Header.h"

// Master socket
int serverSocket;

// Max value needed for select() in linux
int maxSd;
int playersremaining;
Coordinate start;

// Values
int connectedClients;
bool stopFlag = false;

// Objects
bool** grid= new bool*[201];
int* array;

// Default MsgHead
MsgHead responseHead;

int main() {
	Server s;
	s.run(5400, 2);
}

//! Starts the server. The server connects to the given port and waits for maxClients to connect
void Server::run(int port, int maxClients) {
    serverSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    maxSd=serverSocket;

    if(serverSocket==-1) {
        perror("cannot create socket");
        exit(EXIT_FAILURE);
    }

    sockaddr_in hint;
    hint.sin_family=AF_INET;
    hint.sin_port=htons(port);
    hint.sin_addr.s_addr=INADDR_ANY;

    bind(serverSocket, (sockaddr*)&hint, sizeof(hint));
    listen(serverSocket, SOMAXCONN);

    sockaddr_in client;
    int clientsize=sizeof(client);

    connectedClients=0;

while(true) {
    array=new int[connectedClients];
    if(stopFlag){
        break;
    }
    int clientSocket=accept(serverSocket, NULL, NULL);

    if (0>clientSocket){
        perror("Accept failed");
        close(clientSocket);
        exit(EXIT_FAILURE);
    }

    if(clientSocket > maxSd) {
        maxSd=clientSocket;
    }

    connectedClients++;
    responseHead.id++;
    array[responseHead.id]=clientSocket;

    char buffer[256]= { '\0' };
    recv(clientSocket, buffer, sizeof(buffer), 0);
    JoinMsg joinmsg;
    memcpy(&joinmsg, buffer, sizeof(joinmsg));
    std::cout << joinmsg.name << " Has connected to the server!" << std::endl;
    responseHead.type=Join;
    send(clientSocket, (char*)&responseHead, sizeof(responseHead),0);
    responseHead.seq_no++;

    if(connectedClients=maxClients){
        std::cout << "Game has started" << std::endl;
        playersremaining=connectedClients;
        begin();
        
    }
}}

void Server::begin(){ 

    responseHead.type=Change;
    setupGrid();    

    for(int i=0; i < connectedClients; i++) {
        start.x+=10;
        start.y+=10;
        updateGrid(start.x, start.y);

        int id =i+1;
        Coordinate broadcast;
        broadcast.x=start.x;
        broadcast.y=start.y;
        broadcastNewPlayerPos(&id, &broadcast);
    }

    fd_set readSet;
    timeval timeout;

    while(true) {
            if(stopFlag){
        break;
    }
        char buffer[512]={'\0'};

        for (int i=0; i < connectedClients; i++) {

            int clientSocket = array[i+1];

            FD_ZERO(&readSet);
			FD_SET(clientSocket, &readSet);

			timeout.tv_sec = 0;
			timeout.tv_usec = 0;

			// Select ignores input until activity is found on the socket
			int retVal = select(maxSd + 1, &readSet, NULL, NULL, &timeout);

			if (FD_ISSET(clientSocket, &readSet)) {

				recv(clientSocket, buffer, sizeof(buffer), 0);
				MsgHead head;
				memcpy(&head, buffer, sizeof(head));

                responseHead.seq_no++;

                switch(head.type) {
                    case Leave: {
                    LeaveMsg leave;
                    memcpy(&leave, buffer, sizeof(leave));
                    int clientID=leave.head.id;
                    broadcastPlayerLeft(&clientID);
                    if(playersremaining==0){
                        break;
                    }else{
                        playersremaining--;
                    }
                    }
                    case Event:{
                    MoveEvent mEvent;
                    memcpy(&mEvent, buffer, sizeof(mEvent));
                    int clientID=mEvent.event.head.id;
              
                        int tempx = mEvent.pos.x+100;
                        int tempy = mEvent.pos.y+100;
                        if(checkmove(tempx,tempy, clientID)){
                            std::cout << "You lost" << std::endl;
                        }else{
                            updateGrid(tempx,tempy);
                            broadcastNewPlayerPos(&clientID, &mEvent.pos);
                        }
                    }
                   
                }
            }

        }
    }

}
bool Server::alive(int* id) {
    for (int i=0; i<connectedClients; i++) {
        if(array[i]==*id){
            return true;
        }else{
            return false;
        }
    }
}


void Server::broadcastNewPlayerPos(int* clientID, Coordinate* c) {
	// Create change message
	responseHead.id = *clientID;
	responseHead.type = Change;

	ChangeMsg cMsg;
	cMsg.head = responseHead;
	cMsg.type = NewPlayerPosition;

	NewPlayerPositionMsg nppMsg;
	nppMsg.msg = cMsg;
	nppMsg.pos = *c;

	// Send to all clients
	for (int j = 0; j < connectedClients; j++) {
		send(array[j+1], (char*)&nppMsg, sizeof(nppMsg), 0);
		responseHead.seq_no++;
	}
}
 void Server::updateGrid(int x, int y){
     grid[x][y]=true;
     
 }
 bool Server::checkmove(int x, int y, int id) {
     if(x>199 || y>199 || x<0 || y<0){
         std::cout << "Game has ended, a player hit a border" << std::endl;
         close(serverSocket);
         stopFlag=true;
     }
     if (grid[x][y]==true){
        std::cout << "Game has ended due to crashing into another players tile" << std::endl;
        close(serverSocket);
        stopFlag=true;
     }
     return false;
 }    

 
 void Server::setupGrid(){
     for(int i=0;i<201; i++)
 grid[i]=new bool[201];

 for (int i = 0; i < 201; i++) {
     for (int j=0; j <201; j++){
         grid[i][j]=false;
     }
 }
 }
 void Server::broadcastPlayerLeft(int* clientID){
    	// Create change message
	responseHead.id = *clientID;
	responseHead.type = Change;

	ChangeMsg cMsg;
	cMsg.head = responseHead;
	cMsg.type = PlayerLeave;

	PlayerLeaveMsg plMsg;
	plMsg.msg = cMsg;

	// Send to all clients
	for (int j = 0; j < connectedClients; j++) {
		send(array[j], (char*)&plMsg, sizeof(plMsg), 0);
		responseHead.seq_no++;
	}
 }

 
