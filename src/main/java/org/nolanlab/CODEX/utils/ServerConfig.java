package org.nolanlab.CODEX.utils;

public class ServerConfig {

    private String ip;
    private String uploaderCache;
    private int numGPU;
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

    public int getNumGPU() {
        return numGPU;
    }

    public void setNumGPU(int numGPU) {
        this.numGPU = numGPU;
    }

    public int getMaxRAM() {
        return maxRAM;
    }

    public void setMaxRAM(int maxRAM) {
        this.maxRAM = maxRAM;
    }

}
