package uploader.uplclient;

public class ChooseServer {

    private String serverName;
    private ServerConfig[] serverConfigs;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public ServerConfig[] getServerConfigs() {
        return serverConfigs;
    }

    public void setServerConfigs(ServerConfig[] serverConfigs) {
        this.serverConfigs = serverConfigs;
    }
}
