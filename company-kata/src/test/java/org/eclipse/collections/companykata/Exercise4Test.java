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

import java.util.List;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

/**
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/12">Exercise 4 Slides</a>
 */
public class Exercise4Test extends CompanyDomainForKata
{
    /**
     * Solve this without changing the return type of {@link Company#getSuppliers()}. Find the appropriate method on
     * {@link ArrayIterate}.
     */
    @Test
    public void findSupplierNames()
    {
        MutableList<String> supplierNames = null;
        //MutableList以外のリストが混じっていた時の改定が少し大変。
        //根の方を改善する。
        supplierNames = this.company.getSuppliers().collect(Supplier::getName);
        //supplierNames.each(System.out::println);
        MutableList<String> expectedSupplierNames = Lists.mutable.with(
                "Shedtastic",
                "Splendid Crocks",
                "Annoying Pets",
                "Gnomes 'R' Us",
                "Furniture Hamlet",
                "SFD",
                "Doxins");
        Assert.assertEquals(expectedSupplierNames, supplierNames);
    }

    /**
     * Create a {@link Predicate} for Suppliers that supply more than 2 items. Find the number of suppliers that
     * satisfy that Predicate.
     */
    @Test
    public void countSuppliersWithMoreThanTwoItems()
    {
        Predicate<Supplier> moreThanTwoItems = null;
        moreThanTwoItems = supplier-> supplier.getItemNames().size()>2;
        int suppliersWithMoreThanTwoItems = 0;
        suppliersWithMoreThanTwoItems = this.company.getSuppliers().select(moreThanTwoItems).size();
        Assert.assertEquals("suppliers with more than 2 items", 5, suppliersWithMoreThanTwoItems);
    }

    /**
     * Try to solve this without changing the return type of {@link Supplier#getItemNames()}.
     *
     */
    @Test
    public void whoSuppliesSandwichToaster()
    {
        // Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".

        Predicate<Supplier> suppliesToaster = null;
         suppliesToaster =supplier -> supplier.getItemNames().anySatisfy(name->"sandwich toaster".equals(name));

        // Find one supplier that supplies toasters.
        Supplier toasterSupplier = null;
        toasterSupplier = this.company.getSuppliers().detect(suppliesToaster);
        Assert.assertNotNull("toaster supplier", toasterSupplier);
        Assert.assertEquals("Doxins", toasterSupplier.getName());
    }

    @Test
    public void filterOrderValues()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();

        /**
         * Get the order values that are greater than 1.5.
         */
        //別につなげて記述してもよい。
        Predicate<Double> predicate =val -> val>1.5;
        MutableList<Double> orderValues = null;
        orderValues = Lists.mutable.ofAll( orders).collect(Order::getValue);
        MutableList<Double> filtered = null;
        filtered = orderValues.select(predicate);
        Assert.assertEquals(Lists.mutable.with(372.5, 1.75), filtered);
    }

    @Test
    public void filterOrders()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        /**
         * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
         */
        MutableList<Order> filtered = null;
        filtered = Lists.mutable.ofAll(orders).select(order->2.0<order.getValue());
        Assert.assertEquals(Lists.mutable.with(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders())), filtered);
    }
}
