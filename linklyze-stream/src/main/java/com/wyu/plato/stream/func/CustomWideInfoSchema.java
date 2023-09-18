package com.wyu.plato.stream.func;

import com.alibaba.fastjson2.JSON;
import com.wyu.plato.stream.domain.WideInfo;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class CustomWideInfoSchema implements SerializationSchema<WideInfo>, DeserializationSchema<WideInfo> {

    private static final long serialVersionUID = 1L;

    private transient Charset charset;

    public CustomWideInfoSchema() {
        this(StandardCharsets.UTF_8);
    }

    public CustomWideInfoSchema(Charset charset) {
        this.charset = checkNotNull(charset);
    }

    @Override
    public byte[] serialize(WideInfo element) {
        String jsonStr = JSON.toJSONString(element);
        return jsonStr.getBytes(charset);
    }

    @Override
    public WideInfo deserialize(byte[] message) throws IOException {
        String jsonStr = new String(message, charset);
        return JSON.parseObject(jsonStr, WideInfo.class);
    }

    @Override
    public boolean isEndOfStream(WideInfo nextElement) {
        return false;
    }

    @Override
    public TypeInformation<WideInfo> getProducedType() {
        return TypeInformation.of(WideInfo.class);
    }


    // ------------------------------------------------------------------------
    //  Java Serialization
    // ------------------------------------------------------------------------

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(charset.name());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String charsetName = in.readUTF();
        this.charset = Charset.forName(charsetName);
    }
}
