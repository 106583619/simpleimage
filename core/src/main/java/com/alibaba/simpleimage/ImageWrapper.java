/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.simpleimage;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRenderedImage;

import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedImageAdapter;
import javax.media.jai.WritableRenderedImageAdapter;

import org.w3c.dom.Node;

import com.alibaba.simpleimage.util.NodeUtils;

/**
 * �������ڵ��������ڿ��Ա�BufferedImage��PlanarImage�ṩ�����ͼƬ��Դ��Ϣ��ͬʱ���Ա�����GIF�����ัͼƬ��ͼƬ��ʽ�� ����˵���Ա���JPEG��quality������������
 * ����PNG��BMP��GIF����������ѹ����ͼƬ��ʽ��quality��û������ ��������Ϊ93ֻ�Ǳ�ʾ��������ͼƬһ����JPEG��ʽ����Ļ���Ĭ�ϵ�quality��93
 * 
 * @author wendell
 */
public class ImageWrapper extends MetadataRenderedImage {

    public static final int   DEFAULT_QUALITY = 93;

    protected RenderedImage[] images;

    // only support jpeg broken indicator
    protected boolean         broken;

    public ImageWrapper(BufferedImage bi){
        this(bi, DEFAULT_QUALITY, false);
    }

    public ImageWrapper(PlanarImage img){
        this(img, DEFAULT_QUALITY, false);
    }

    public ImageWrapper(BufferedImage bi, boolean isBroken){
        this(bi, DEFAULT_QUALITY, isBroken);
    }

    public ImageWrapper(PlanarImage img, boolean isBroken){
        this(img, DEFAULT_QUALITY, isBroken);
    }

    public ImageWrapper(BufferedImage bi, int quality){
        this(bi, quality, false);
    }

    public ImageWrapper(BufferedImage bi, int quality, boolean isBroken){
        this.quality = quality;
        this.images = new RenderedImage[1];
        this.images[0] = PlanarImage.wrapRenderedImage(bi);
        this.broken = isBroken;
    }

    public ImageWrapper(PlanarImage image, int quality){
        this(image, quality, false);
    }

    public ImageWrapper(PlanarImage image, int quality, boolean isBroken){
        this.images = new RenderedImage[1];
        this.images[0] = image;
        this.quality = quality;
        this.broken = isBroken;
    }

    public ImageWrapper(BufferedImage[] imgs){
        setImages(imgs);
        this.quality = DEFAULT_QUALITY;
    }

    public ImageWrapper(PlanarImage[] imgs){
        setImages(imgs);
        this.quality = DEFAULT_QUALITY;
    }

    public BufferedImage getAsBufferedImage(int index) {
        if (images[index] instanceof BufferedImage) {
            return (BufferedImage) images[index];
        } else if (images[index] instanceof PlanarImage) {
            return ((PlanarImage) images[index]).getAsBufferedImage();
        } else if (images[index] instanceof WritableRenderedImage) {
            return new WritableRenderedImageAdapter((WritableRenderedImage) images[index]).getAsBufferedImage();
        } else {
            return new RenderedImageAdapter(images[index]).getAsBufferedImage();
        }
    }

    public BufferedImage getAsBufferedImage() {
        return getAsBufferedImage(0);
    }

    public PlanarImage getAsPlanarImage(int index) {
        if (images[index] instanceof PlanarImage) {
            return (PlanarImage) images[index];
        } else if (images[index] instanceof BufferedImage) {
            return PlanarImage.wrapRenderedImage(images[index]);
        } else {
            return new RenderedImageAdapter(images[index]);
        }
    }

    public PlanarImage getAsPlanarImage() {
        return getAsPlanarImage(0);
    }

    public BufferedImage[] getAsBufferedImages() {
        BufferedImage[] imgs = new BufferedImage[images.length];

        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = getAsBufferedImage(i);
        }

        return imgs;
    }

    public PlanarImage[] getAsPlanarImages() {
        PlanarImage[] imgs = new PlanarImage[images.length];

        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = getAsPlanarImage(i);
        }

        return imgs;
    }

    public void setImages(BufferedImage[] imgs) {
        images = new RenderedImage[imgs.length];
        for (int i = 0; i < imgs.length; i++) {
            images[i] = imgs[i];
        }
    }

    public void setImages(PlanarImage[] imgs) {
        images = new RenderedImage[imgs.length];
        for (int i = 0; i < imgs.length; i++) {
            images[i] = imgs[i];
        }
    }

    public void setImage(int index, BufferedImage bi) {
        this.images[index] = bi;
    }

    public void setImage(BufferedImage bi) {
        setImage(0, bi);
    }

    public void setImage(int index, PlanarImage img) {
        this.images[index] = img;
    }

    public void setImage(PlanarImage img) {
        setImage(0, img);
    }

    public int getNumOfImages() {
        return images.length;
    }

    /**
     * ����ǳ�GIF�����ͼƬ��getWidth()��getWidth(0)�ȼۣ��ȷ��ص�һ��ͼƬ�Ŀ�� �����GIF�����ȡGIF��Ԫ��Ϣ����ȡͼƬ��ȣ����ֵ��һ����getWidth(0)���
     * 
     * @return ͼƬ���
     */
    public int getWidth() {
        if (format == ImageFormat.GIF && streamMetadata != null) {
            Node screenDescNode = NodeUtils.getChild(streamMetadata, "LogicalScreenDescriptor");
            if (screenDescNode != null) {
                return NodeUtils.getIntAttr(screenDescNode, "logicalScreenWidth");
            }
        }

        return getWidth(0);
    }

    /**
     * ����ǳ�GIF�����ͼƬ��getHeight()��getHeight(0)�ȼۣ��ȷ��ص�һ��ͼƬ�Ŀ�� �����GIF�����ȡGIF��Ԫ��Ϣ����ȡͼƬ�߶ȣ����ֵ��һ����getHeight(0)���
     * 
     * @return ͼƬ�߶�
     */
    public int getHeight() {
        if (format == ImageFormat.GIF && streamMetadata != null) {
            Node screenDescNode = NodeUtils.getChild(streamMetadata, "LogicalScreenDescriptor");
            if (screenDescNode != null) {
                return NodeUtils.getIntAttr(screenDescNode, "logicalScreenHeight");
            }
        }

        return getHeight(0);
    }

    public int getWidth(int index) {
        if (index < 0 || index >= images.length) {
            throw new IndexOutOfBoundsException("Just totally have " + images.length + " images");
        }

        return images[index].getWidth();
    }

    public int getHeight(int index) {
        if (index < 0 || index >= images.length) {
            throw new IndexOutOfBoundsException("Just totally have " + images.length + " images");
        }

        return images[index].getHeight();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        int numOfImages = images.length;
        BufferedImage[] imgs = new BufferedImage[numOfImages];
        for (int i = 0; i < numOfImages; i++) {
            PlanarImage oldImg = getAsPlanarImage(i);
            imgs[i] = oldImg.getAsBufferedImage();
        }

        ImageWrapper newImgWrapper = new ImageWrapper(imgs);
        newImgWrapper.quality = quality;
        newImgWrapper.broken = broken;
        newImgWrapper.format = format;
        if (horizontalSamplingFactors != null) {
            newImgWrapper.horizontalSamplingFactors = horizontalSamplingFactors.clone();
        }
        if (verticalSamplingFactors != null) {
            newImgWrapper.verticalSamplingFactors = verticalSamplingFactors.clone();
        }
        if (streamMetadata != null) {
            newImgWrapper.streamMetadata = NodeUtils.cloneNode(streamMetadata);
        }
        if (metadatas != null) {
            newImgWrapper.metadatas = new Node[metadatas.length];
            for (int i = 0; i < metadatas.length; i++) {
                newImgWrapper.metadatas[i] = NodeUtils.cloneNode(metadatas[i]);
            }
        }

        return newImgWrapper;
    }

    /**
     * �������ֻ��ͼƬ��ʽ��JPEG��ʱ��������壬��ʾͼƬ�����Ѿ���
     * 
     * @return the broken
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * �������ֻ��ͼƬ��ʽ��JPEG��ʱ��������壬��ʾͼƬ�����Ѿ���
     * 
     * @param broken the broken to set
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
