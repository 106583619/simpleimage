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
package com.alibaba.simpleimage.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


/**
 * ��ReleatePositionDrawTextItem.java��ʵ��������TODO ��ʵ������ 
 * @author wendell 2011-3-28 ����01:43:45
 */
public class ReleatePositionDrawTextItem extends DrawTextItem {
     // ��Ҫ���ı�ռͼƬ��ȵİٷֱ�,����0.85,0.95
     protected float textWidthPercent;
     
     protected float xFactor;
     protected float yFactor;
    /**
     * @param text
     * @param fontColor
     * @param fontShadowColor
     * @param font
     * @param minFontSize
     * @param textWidthPercent
     */
    public ReleatePositionDrawTextItem(String text, Color fontColor, Color fontShadowColor, Font font, int minFontSize,
                                       float textWidthPercent, float xFactor, float yFactor){
        super(text, fontColor, fontShadowColor, font, minFontSize);
        this.textWidthPercent = textWidthPercent;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    /* (non-Javadoc)
     * @see com.alibaba.simpleimage.render.DrawTextItem#drawText(java.awt.Graphics2D, int, int)
     */
    @Override
    public void drawText(Graphics2D graphics, int width, int height) {
        int textLength = (int) (width * textWidthPercent);
        // ����ˮӡ�����С
        int fontsize = textLength / text.length();
        if(fontsize < minFontSize) {
            return ;
        }

        graphics.setFont(new Font(defaultFont.getFontName(), Font.PLAIN, fontsize));
        graphics.setColor(fontColor);
        graphics.drawString(text, (int)(width * xFactor), (int)(height * yFactor));
    }

}
