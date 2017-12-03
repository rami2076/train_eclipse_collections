/*
 * Copyright (c) 2017 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.companykata;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/8">Exercise 3 Slides</a>
 */
public class Exercise3Test extends CompanyDomainForKata
{
    /**
     * Improve {@link Company#getOrders()} without breaking this test.
     * getOrdersメソッドを改善しよう。
     */
    @Test
    public void improveGetOrders()
    {
        // Delete this line - it's a reminder
        //Assert.fail("Improve getOrders() without breaking this test");
        Verify.assertSize(5, this.company.getOrders());
    }

    /**
     * Get all items that have been ordered by anybody.
     */
    @Test
    public void findItemNames()
    {
        MutableList<LineItem> allOrderedLineItems = null;
        MutableSet<String> actualItemNames = null;

        allOrderedLineItems = this.company.getOrders().flatCollect(Order::getLineItems);
        actualItemNames = allOrderedLineItems.collect(LineItem::getName).toSet();
        Verify.assertInstanceOf(MutableSet.class, actualItemNames);
        //TODO:: なぜかMutableSet Object　のgetFirstが非推奨になっていた。リストにして対応。
        //なぜかの原因は探っていないがアイテムを持つ構造に問題があるのではと推測
        Verify.assertInstanceOf(String.class, actualItemNames.toList().getFirst());

        MutableSet<String> expectedItemNames = Sets.mutable.with(
                "shed", "big shed", "bowl", "cat", "cup", "chair", "dog",
                "goldfish", "gnome", "saucer", "sofa", "table");
        Assert.assertEquals(expectedItemNames, actualItemNames);
    }

    @Test
    public void findCustomerNames()
    {
        MutableList<String> names = null;
        names = this.company.getCustomers().collect(Customer::getName);
        MutableList<String> expectedNames = Lists.mutable.with("Fred", "Mary", "Bill");
        Assert.assertEquals(expectedNames, names);
    }
}
