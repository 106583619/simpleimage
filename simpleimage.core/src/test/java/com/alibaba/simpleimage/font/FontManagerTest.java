/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.simpleimage.font;

import java.awt.Font;

import junit.framework.TestCase;


/**
 * ��FontManagerTest.java��ʵ��������TODO ��ʵ������ 
 * @author wendell 2011-5-9 ����01:52:26
 */
public class FontManagerTest extends TestCase {

    public void testFounderBlack() throws Exception {
        Font font = FontManager.getFont("��������");
        assertNotNull(font);
    }
}
