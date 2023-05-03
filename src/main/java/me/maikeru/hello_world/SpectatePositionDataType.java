package me.maikeru.hello_world;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class SpectatePositionDataType implements PersistentDataType<byte[], SpectatePositionInformation>{

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<SpectatePositionInformation> getComplexType() {
        return SpectatePositionInformation.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull SpectatePositionInformation complex, @NotNull PersistentDataAdapterContext context) {
        return SerializationUtils.serialize(complex);
    }

    @Override
    public SpectatePositionInformation fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            InputStream is = new ByteArrayInputStream(primitive);
            ObjectInputStream o = new ObjectInputStream(is);
            return (SpectatePositionInformation) o.readObject();

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
