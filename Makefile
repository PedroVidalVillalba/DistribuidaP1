# Java Compiler
JC = javac

# Compilation flags
JFLAGS = 

# Sources
SRC_DIR = src
SERVER_SRC = $(SRC_DIR)/server/*.java
CLIENT_SRC = $(SRC_DIR)/client/*.java

# Output directory
OUT_DIR = out

all: server client

server: $(SERVER_SRC)
	$(JC) $(JFLAGS) -d $(OUT_DIR) $(SERVER_SRC)

client: $(CLIENT_SRC)
	$(JC) $(JFLAGS) -d $(OUT_DIR) $(CLIENT_SRC)

clean: 
	rm -rf $(OUT_DIR)/*
