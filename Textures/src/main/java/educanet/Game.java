package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Game {

    private static final float[] vertices = {
            0.5f, 0.5f, 0.0f, // 0 -> Top right
            0.5f, -0.5f, 0.0f, // 1 -> Bottom right
            -0.5f, -0.5f, 0.0f, // 2 -> Bottom left
            -0.5f, 0.5f, 0.0f, // 3 -> Top left
    };

    private static final float[] colors = {
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f,
    };

    private static final int[] indices = {
            0, 1, 3, // First triangle
            1, 2, 3 // Second triangle
    };

    // Sprite

    private static final float[] sprite1 = {
            0.1f, 0.0f,
            0.1f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
    };

    private static final float[] sprite2 = {
            0.3f, 0.0f,
            0.3f, 1.0f,
            0.16f, 1.0f,
            0.16f, 0.0f,
    };

    private static final float[] sprite3 = {
            0.5f, 0.0f,
            0.5f, 1.0f,
            0.3f, 1.0f,
            0.3f, 0.0f,
    };

    private static final float[] sprite4 = {
            0.6f, 0.0f,
            0.6f, 1.0f,
            0.5f, 1.0f,
            0.5f, 0.0f,
    };

    private static final float[] sprite5 = {
            0.8f, 0.0f,
            0.8f, 1.0f,
            0.6f, 1.0f,
            0.6f, 0.0f,
    };

    private static final float[] sprite6 = {
            0.9f, 0.0f,
            0.9f, 1.0f,
            0.8f, 1.0f,
            0.8f, 0.0f,
    };

    private static int squareVaoId;
    private static int squareVboId;
    private static int squareEboId;
    private static int colorsId;
    private static int textureIndicesId;

    private static int textureId;

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();

        // Generate all the ids
        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        colorsId = GL33.glGenBuffers();
        textureIndicesId = GL33.glGenBuffers();

        textureId = GL33.glGenTextures();
        loadImage();

        // Tell OpenGL we are currently using this object (vaoId)
        GL33.glBindVertexArray(squareVaoId);

        // Tell OpenGL we are currently writing to this buffer (eboId)
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        // Change to VBOs...
        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(fb);

        // Change to Color...
        // Tell OpenGL we are currently writing to this buffer (colorsId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorsId);

        FloatBuffer cb = BufferUtils.createFloatBuffer(colors.length)
                .put(colors)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(cb);
    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);

        // Draw using the glDrawElements function
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, textureId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    private static FloatBuffer tb = BufferUtils.createFloatBuffer(10);
    private static int cycleNum = 1;
    private static float speed = (float) 0.5;

    public static void update(long window) {

        if(cycleNum == 1 || cycleNum == 1.5) {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear();
            tb.put(sprite1).flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("1: " + cycleNum);
            cycleNum += speed;
        }
        if(cycleNum == 2 || cycleNum == 2.5) {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear();
            tb.put(sprite2).flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("2: " + cycleNum);
            cycleNum += speed;
        }
        if(cycleNum == 3 || cycleNum == 3.5) {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear();
            tb.put(sprite3).flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("3: " + cycleNum);
            cycleNum += speed;
        }
        if(cycleNum == 4 || cycleNum == 4.5) {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear();
            tb.put(sprite4).flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("4: " + cycleNum);
            cycleNum += speed;
        }
        if(cycleNum == 5 || cycleNum == 5.5) {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear();
            tb.put(sprite5).flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("5: " + cycleNum);
            cycleNum += speed;
        }
        if(cycleNum == 6 || cycleNum == 6.5) {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear();
            tb.put(sprite6).flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("6: " + cycleNum);
            cycleNum = 1;
        }


    }

    private static void loadImage() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            ByteBuffer img = STBImage.stbi_load("resources/textures/Cyborg_run.png", w, h, comp, 3);
            if(img != null) {
                img.flip();

                GL33.glBindTexture(GL33.GL_TEXTURE_2D, textureId);
                GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGB, w.get(), h.get(), 0, GL33.GL_RGB, GL33.GL_UNSIGNED_BYTE, img);
                GL33.glGenerateMipmap(GL33.GL_TEXTURE_2D);

                STBImage.stbi_image_free(img);
            }
        }
    }

}
