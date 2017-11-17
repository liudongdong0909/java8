package com.donggua.nio.pipe;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-11-03 下午 05:38
 */
public class PipeChannelTest {

    @Test
    public  void testPipe() throws IOException{
        String string = "doggua 测试 PIPE";

        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();
        byteBuffer.put(string.getBytes());
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()){
            sinkChannel.write(byteBuffer);
        }

        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer sourceBuffer = ByteBuffer.allocate(1024);
        sourceChannel.read(sourceBuffer);


    }
}
