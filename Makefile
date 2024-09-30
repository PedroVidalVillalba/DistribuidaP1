# Java Compiler
JC = javac

# Compilation flags
JFLAGS = 

# Sources
SRC_DIR = src
SRC_SERVER = $(SRC_DIR)/server/BingoDrum.java $(SRC_DIR)/server/Server.java
SRC_CLIENT = $(SRC_DIR)/client/Cardboard.java $(SRC_DIR)/client/Client.java
SRC = $(SRC_SERVER) $(SRC_CLIENT)

# Output directory
OUT_DIR = out

all: $(SRC)
	$(JC) $(JFLAGS) -d $(OUT_DIR) $(SRC)

# server: $(SERVER_SRC)
# 	$(JC) $(JFLAGS) -d $(OUT_DIR) $(SERVER_SRC)

# client: $(CLIENT_SRC)
# 	$(JC) $(JFLAGS) -d $(OUT_DIR) $(CLIENT_SRC)

clean: 
	rm -rf $(OUT_DIR)/*
