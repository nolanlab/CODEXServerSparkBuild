package uploader.uplclient;

public class ServerConfig {

    private String ip;
    private String uploaderCache;
    private int numGpu;
    private int maxRAM;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUploaderCache() {
        return uploaderCache;
    }

    public void setUploaderCache(String uploaderCache) {
        this.uploaderCache = uploaderCache;
    }

    public int getNumGpu() {
        return numGpu;
    }

    public void setNumGpu(int numGpu) {
        this.numGpu = numGpu;
    }

    public int getMaxRAM() {
        return maxRAM;
    }

    public void setMaxRAM(int maxRAM) {
        this.maxRAM = maxRAM;
    }

}
