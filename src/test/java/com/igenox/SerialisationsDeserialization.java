package com.igenox;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * @author alexandru.ionita
 * @since 1.0.0
 */
public class SerialisationsDeserialization
{

    private Log4jLogEvent event;

    @Before
    public void setup()
    {
        event = new Log4jLogEvent.Builder()
                .setLoggerName( "logger_test" )
                .setMessage( new ParameterizedMessage( "This is some message {} {}", 1, 2 ) )
                .setLevel( Level.DEBUG )
                .build();
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSerialisationsDeserialization_fails() throws IOException, ClassNotFoundException
    {
        Serializable ser = Log4jLogEvent.serialize( event, true );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( ser );

        byte[] bytes = baos.toByteArray();

        ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( bytes ) );

        //  the deserialization fails
        Log4jLogEvent.deserialize( (Serializable) ois.readObject() );
    }

    @Test
    public void testSerialisationsDeserialization_succeeds() throws IOException, ClassNotFoundException
    {
        Serializable ser = Log4jLogEvent.serialize( event, true );
        Log4jLogEvent.deserialize( ser );
    }
}
