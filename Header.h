#pragma once

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <iostream>
#include "Protocol.h"
#include <thread>
#include <vector>
#include <string>

#define SCK_version 2 0x0202


class Server {
public:
	// Methods
	void run(int port, int maxClients);
	void begin();

    void updateGrid(int x, int y);
	void gridMove(Coordinate c);
	void setupGrid();

	void broadcastNewPlayer(unsigned int* clientID, const char* name);
	void broadcastNewPlayerPos(int* clientID, Coordinate* c);
	void broadcastPlayerLeft(int* clientID);
	bool alive(int* id);
	void removePlayer(int* id);
	bool checkmove(int x, int y,int id);
	void shutDown();

};