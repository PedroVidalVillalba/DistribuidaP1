# Java Compiler
JC = javac

JAR = jar

# Compilation flags
JFLAGS =

# Sources
SRC_DIR = src

SERVER_DIR = server
CLIENT_DIR = client

SRC_SERVER = BingoDrum.java Server.java ServerListener.java ServerMain.java
SRC_CLIENT = Cardboard.java Client.java ClientMain.java

SERVER_SRC_PATH = $(addprefix $(SRC_DIR)/$(SERVER_DIR)/, $(SRC_SERVER))
CLIENT_SRC_PATH = $(addprefix $(SRC_DIR)/$(CLIENT_DIR)/, $(SRC_CLIENT))

SRC = $(SRC_SERVER) $(SRC_CLIENT)

# Output directory
OUT_DIR = bingo

OUT_SERVER = $(SRC_SERVER:.java=.class)
OUT_CLIENT = $(SRC_CLIENT:.java=.class)

SERVER_OUT_PATH = $(addprefix $(OUT_DIR)/$(SERVER_DIR)/, $(OUT_SERVER))
CLIENT_OUT_PATH = $(addprefix $(OUT_DIR)/$(CLIENT_DIR)/, $(OUT_CLIENT))

# Properties file 
PROPERTIES = properties/bingo.properties

# JAR files
JAR_SERVER = server.jar
JAR_CLIENT = client.jar

SERVER_MAIN = $(OUT_DIR).$(SERVER_DIR).ServerMain
CLIENT_MAIN = $(OUT_DIR).$(CLIENT_DIR).ClientMain

all: jar_server jar_client

jar_server: server $(PROPERTIES)
	$(JAR) -cfe $(JAR_SERVER) $(SERVER_MAIN) $(SERVER_OUT_PATH) $(PROPERTIES)  

jar_client: client $(PROPERTIES)
	$(JAR) -cfe $(JAR_CLIENT) $(CLIENT_MAIN) $(CLIENT_OUT_PATH) $(PROPERTIES)

server: $(SERVER_SRC_PATH)
	$(JC) $(JFLAGS) -d . $(SERVER_SRC_PATH)

client: $(CLIENT_SRC_PATH)
	$(JC) $(JFLAGS) -d . $(CLIENT_SRC_PATH)

clean: 
	find . -name "*.o" -delete
	find . -name "*.jar" -delete
	rm -r $(OUT_DIR)
