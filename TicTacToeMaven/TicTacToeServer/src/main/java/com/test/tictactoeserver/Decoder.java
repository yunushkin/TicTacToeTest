package com.test.tictactoeserver;

import com.test.tictactoeserver.Decoder.DecoderState;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

public class Decoder extends ReplayingDecoder<DecoderState>
{
    public enum DecoderState
    {
        READ_LENGTH,
        READ_CONTENT;
    }  

    private int length;

    public Decoder()
    {
        super( DecoderState.READ_LENGTH );
    }
    
    @Override
    protected Object decode( ChannelHandlerContext chc, Channel chnl, ChannelBuffer cb, DecoderState state ) throws Exception
    {
        switch ( state )
        {
            case READ_LENGTH:
                length = cb.readInt();
                checkpoint( DecoderState.READ_CONTENT );
                
            case READ_CONTENT:
                ChannelBuffer frame = cb.readBytes( length );
                checkpoint( DecoderState.READ_LENGTH );
                return frame;
                
            default:
                throw new Error( "Shouldn't reach here." );
        }
    }
}
