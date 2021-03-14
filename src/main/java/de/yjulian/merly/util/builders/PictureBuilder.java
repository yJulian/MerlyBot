package de.yjulian.merly.util.builders;

import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class PictureBuilder {

    private int height = 500, width = 500;
    private Graphics2D g;
    private BufferedImage bImg;

    /**
     * Create a new picture builder with the size 500x500.
     */
    public PictureBuilder() {
        init();
    }

    /**
     * Create a new picture builder with variable size.
     * @param height the height.
     * @param width the width.
     */
    public PictureBuilder(int height, int width) {
        this.height = height;
        this.width = width;
        init();
    }

    private void init(){
        bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = bImg.createGraphics();
    }

    public Graphics2D getGraphics() {
        return g;
    }

    public PictureBuilder draw(Consumer<Graphics2D> consumer){
        consumer.accept(g);
        return this;
    }

    public InputStream getAsInputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bImg, "PNG", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    public byte[] getAsByteArray() throws IOException {
        return IOUtils.toByteArray(getAsInputStream());
    }

    /**
     * Send the previous drawn picture in {@link #draw(Consumer)} into a text Channel
     * @param channel The TextChannel to send it to
     * @param name The name of the picture. Ending added automatically
     * @throws IOException when the image cannot be exported.
     */
    public void send(TextChannel channel, String name) throws IOException {
        channel.sendFile(getAsByteArray(), name + ".png").queue();
    }

}
