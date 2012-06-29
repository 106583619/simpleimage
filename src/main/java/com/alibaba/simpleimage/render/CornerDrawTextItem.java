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
import java.awt.font.FontRenderContext;

import org.apache.commons.lang.StringUtils;

/**
 * ��CornerDrawTextItem.java��ʵ��������TODO ��ʵ������
 * 
 * @author wendell 2011-3-28 ����02:29:15
 */
public class CornerDrawTextItem extends FixDrawTextItem {

    static final float DEFAULT_CORNER_TEXT_WIDTH_PER = 0.5f;

    public CornerDrawTextItem(String text){
        super(text, FONT_COLOR, FONT_SHADOW_COLOR, FONT, MIN_FONT_SIZE, null, DEFAULT_CORNER_TEXT_WIDTH_PER);
    }

    public CornerDrawTextItem(String text, float textWidthPercent){
        super(text, FONT_COLOR, FONT_SHADOW_COLOR, FONT, MIN_FONT_SIZE, null, textWidthPercent);
    }

    /**
     * @param text
     * @param fontColor
     * @param fontShadowColor
     * @param font
     * @param minFontSize
     */
    public CornerDrawTextItem(String text, Color fontColor, Color fontShadowColor, Font font, int minFontSize,
                              float textWidthPercent){
        super(text, fontColor, fontShadowColor, font, minFontSize, null, textWidthPercent);
    }

    /*
     * (non-Javadoc)
     * @see com.alibaba.simpleimage.render.DrawTextItem#drawText(java.awt.Graphics2D, int, int)
     */
    @Override
    public void drawText(Graphics2D graphics, int width, int height) {
        if(StringUtils.isBlank(text)) {
            return ;
        }
        
        int x = 0, y = 0;
        // ����ˮӡ�����ܳ���
        int textLength = (int) (width * textWidthPercent);
        // ����ˮӡ�����С
        int fontsize = textLength / text.length();
        // ̫С��.....����ʾ
        if (fontsize < minFontSize) {
            return ;
        }

        float fsize = (float)fontsize;
        Font font = defaultFont.deriveFont(fsize);
        graphics.setFont(font);
        FontRenderContext context = graphics.getFontRenderContext();
        int sw = (int) font.getStringBounds(text, context).getWidth();

        // �������������
        if (width > height) {
            y = height / 4;
        } else {
            y = width / 4;
        }

        int halflen = sw / 2;
        if (halflen <= (y - fontsize)) {
            x = y - halflen;
        } else {
            x = fontsize;
        }

        if(x <= 0 || y <= 0) {
            return ;
        }
        
        if (fontShadowColor != null) {
            graphics.setColor(fontShadowColor);
            graphics.drawString(text, x + getShadowTranslation(fontsize), y + getShadowTranslation(fontsize));
        }
        graphics.setColor(fontColor);
        graphics.drawString(text, x, y);

        if (width > height) {
            y = height - (height / 4);
        } else {
            y = height - (width / 4);
        }

        halflen = sw / 2;
        if (halflen <= (height - y - fontsize)) {
            x = width - (height - y) - halflen;
        } else {
            x = width - sw - fontsize;
        }
        
        if(x <= 0 || y <= 0) {
            return ;
        }

        if (fontShadowColor != null) {
            graphics.setColor(fontShadowColor);
            graphics.drawString(text, x + getShadowTranslation(fontsize), y + getShadowTranslation(fontsize));
        }
        graphics.setColor(fontColor);
        graphics.drawString(text, x, y);

    }

}
