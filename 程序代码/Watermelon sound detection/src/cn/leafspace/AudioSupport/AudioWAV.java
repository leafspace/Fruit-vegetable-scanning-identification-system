package cn.leafspace.AudioSupport;

import java.util.ArrayList;

public class AudioWAV {
    private static String fileType = "wav";                                  //文件类型
    private int channels;                                                    //声道数
    private int sampleFrequency;                                             //采样频率
    private int samplingResolution;                                          //采样位数
    private ArrayList<String> dataList;                                      //数据可变数组

    public AudioWAV(int samplingResolution, int channels, int sampleFrequency) {
        this.samplingResolution = samplingResolution;
        this.channels = channels;
        this.sampleFrequency = sampleFrequency;
    }

    public void pushData(double sampleData) {
        this.dataList.add(sampleData + "");
    }

    public double popData(int index) {
        if (index < 0 | index >= this.dataList.size()) {
            index = this.dataList.size() - 1;
        }

        try {
            String sampleData = this.dataList.get(index);
            return Double.parseDouble(sampleData);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public int getChannels() {
        return this.channels;
    }

    public int getSampleFrequency() {
        return this.sampleFrequency;
    }

    public int getSamplingResolution() {
        return this.samplingResolution;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void setSampleFrequency(int sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
    }

    public void setSamplingResolution(int samplingResolution) {
        this.samplingResolution = samplingResolution;
    }
}
