package cn.leafspace.AudioSupport;

class AudioByteStruct {
    public int channelsSpace;                                               //声道数
    public int sampleFrequencySpace;                                        //采样频率
    public int samplingResolutionSpace;                                     //采样位数

    public int channelsSizeByte;                                            //声道数
    public int sampleFrequencySizeByte;                                     //采样频率
    public int samplingResolutionSizeByte;                                  //采样位数

    public AudioByteStruct(int channelsSpace, int sampleFrequencySpace, int samplingResolutionSpace,
                           int channelsSizeByte, int sampleFrequencySizeByte, int samplingResolutionSizeByte) {
        this.channelsSpace = channelsSpace;
        this.sampleFrequencySpace = sampleFrequencySpace;
        this.samplingResolutionSpace = samplingResolutionSpace;

        this.channelsSizeByte = channelsSizeByte;
        this.sampleFrequencySizeByte = sampleFrequencySizeByte;
        this.samplingResolutionSizeByte = samplingResolutionSizeByte;
    }
}

public class AudioFile {
    public static AudioByteStruct wav =
            new AudioByteStruct(22, 24, 16, 2, 4, 4);


}
