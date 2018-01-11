package com.elevationstudios.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.elevationstudios.framework.Graphics;
import com.elevationstudios.framework.Pixmap;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer; // represents our artificial framebuffer
    Canvas canvas;		// use to draw to the artificial framebuffer
    Paint paint;		// needed for drawing
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    public void drawPixmapScaled(Pixmap pixmap, int x, int y, int finalX, int finalY) {
        srcRect.left = 0;
        srcRect.top = 0;
        srcRect.right = pixmap.getWidth()-1;
        srcRect.bottom = pixmap.getHeight()-1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = finalX-1;
        dstRect.bottom = finalY-1;
        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    public void drawPixmapScaled(Pixmap pixmap, int x, int y, float scale) {
        srcRect.left = 0;
        srcRect.top = 0;
        srcRect.right = pixmap.getWidth();
        srcRect.bottom = pixmap.getHeight();

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + (int)(srcRect.right*scale)-1;
        dstRect.bottom = y + (int)(srcRect.bottom*scale)-1;
        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
    }

    @Override
    public void drawText(String string, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25.0f);

        canvas.drawText(string, x, y, paint);
    }

    @Override
    public void drawText(String string, int x, int y, float textSize) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);

        canvas.drawText(string, x, y, paint);
    }

    @Override
    public void drawText(String string, int x, int y, float textSize, Paint color) {
        paint.setTextSize(textSize);

        canvas.drawText(string, x, y, color);
    }


    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}