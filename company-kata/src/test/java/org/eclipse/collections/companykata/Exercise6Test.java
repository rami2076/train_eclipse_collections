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

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/17">Exercise 6 Slides</a>
 */
public class Exercise6Test extends CompanyDomainForKata {
	/**
	 * Get a list of the customers' total order values, sorted. Check out the implementation of {@link
	 * Customer#getTotalOrderValue()} and {@link Order#getValue()} .
	 */

	@Test
	public void sortedTotalOrderValue() {
		MutableList<Double> sortedTotalValues = this.company.getCustomers().asLazy().// Option::asLasy()
				collect(Customer::getTotalOrderValue).toSortedList();// Point::toSortedList()
		// MutableListの機能を使いこなすための練習課題。
		// Don't forget the handy utility methods getFirst() and getLast()...
		Assert.assertEquals("Highest total order value", Double.valueOf(857.0), sortedTotalValues.getLast());// Point::getLast()
		Assert.assertEquals("Lowest total order value", Double.valueOf(71.0), sortedTotalValues.getFirst());// Point::getLast()
	}

	/**
	 * Find the max total order value across all customers.
	 */

	/*
	 *全顧客情報を横断し合計注文価格の最大値を見つける
	*/
	@Test
	public void maximumTotalOrderValue() {
		Double maximumTotalOrderValue = this.company.getCustomers()
				.asLazy()
				.collectDouble(Customer::getTotalOrderValue)
				.max();// Point::max
		Assert.assertEquals("max value", Double.valueOf(857.0), maximumTotalOrderValue);
	}

	/**
	 * Find the customer with the highest total order value.
	 */
	/*
	 * 合計注文価格が最大の顧客を見つける。
	*/
	@Test
	public void customerWithMaxTotalOrderValue() {
		Customer customerWithMaxTotalOrderValue = this.company.getCustomers().asLazy().maxBy(
				Customer::getTotalOrderValue);// Point::maxBy(); detectとかcompetateとかを使用して別の記述ができそう。。

		Assert.assertEquals(this.company.getCustomerNamed("Mary"), customerWithMaxTotalOrderValue);
	}

	/**
	 * Create some code to get the company's supplier names as a tilde delimited string.
	 *コレクションしたsupplierの名前を一つのシークエンスにする。区切り文字はチルダ。
	 *
	 *makeStringメソッドを使用する。
	 */
	@Test
	public void supplierNamesAsTildeDelimitedString() {
		String tildeSeparatedNames = this.company.getSuppliers().
				asLazy().
				collect(Supplier::getName).
				makeString("~");

		Assert.assertEquals(
				"tilde separated names",
				"Shedtastic~Splendid Crocks~Annoying Pets~Gnomes 'R' Us~Furniture Hamlet~SFD~Doxins",
				tildeSeparatedNames);
	}

	/**
	 * Deliver all orders going to customers from London.
	 *
	 * <p/>
	 * Hint: Use {@link RichIterable#each(Procedure)}.
	 *参照アドレスは同じなので以下の
	 * @see Order#deliver()
	 */
	@Test
	public void deliverOrdersToLondon() {

		this.company.getCustomers().
		asLazy().
		select(custermer->"London".equals( custermer.getCity())).
		flatCollect(Customer::getOrders).
		each(Order::deliver);

		Verify.assertAllSatisfy(this.company.getCustomerNamed("Fred").getOrders(), Order::isDelivered);
		Verify.assertNoneSatisfy(this.company.getCustomerNamed("Mary").getOrders(), Order::isDelivered);
		Verify.assertAllSatisfy(this.company.getCustomerNamed("Bill").getOrders(), Order::isDelivered);
	}
}
