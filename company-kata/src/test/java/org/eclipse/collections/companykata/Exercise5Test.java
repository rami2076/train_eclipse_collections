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
import org.eclipse.collections.api.list.primitive.DoubleList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.primitive.DoublePredicates;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

/*
static utilityを使わずにリファクタリングしましょうとのこと。
以下がそれの一覧ということであるが、
静的なutilityを使わずの意味がいまいちつかめなかった。
getOrdersのメソッド内の値を静的なメソッドを使用して変換せず
fieldの値を静的なメソッドで初期化することで対応できたのではと考えてはいる。
STATIC UTILITY CHEAT SHEET
Iterate Iterable)
ListIterate (List)
ArrayListIterate (ArrayList)
RandomAccessListIterate (List & RandomAccess)
MapIterate (Map)
LazyIterate (Iterable)
ArrayIterate (T[])
StringIterate (String)
ParallelIterate (Iterable)
ParallelMapIterate (Map)
ParallelArrayIterate (T[])


POINT　predicateに複数の型のものが用意されている
Predicates.greaterThan(50)
"1a2a3", CharPredicate.IS_DIGIT
array, Predicates.greaterThan(50)

色々あるので確認


互換性もある
List<Integer> list = ...;

MutableList<Integer> selected =
  ListIterate.select(list, Predicates.greaterThan(50));

並列処理を行う際に使用する
List<Integer> list = ...;

Collection<Integer> selected =
  ParallelIterate.select(list, Predicates.greaterThan(50));
  locks, threads, pools, executors, etcがないことに注目。
  使い方は分からないけどLezyでパラレルなんだと思う（？）
  TODO:: 検証案件ですね


*/

/**
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/15">Exercise 5 Slides</a>
 */
public class Exercise5Test extends CompanyDomainForKata {
	@Test
	public void filterOrderValues() {

		/**
		 * Same exercise but don't use static utility - refactor the type of orders and {@link Customer#getOrders()}
		 * instead.
		 * Get the order values that are greater than 1.5.
		 */
		// 方法1 一回で抽出までしてしまう。
		// refer
		// https://github.com/haasted/eclipse-collections-kata/blob/solutions/company-kata/src/main/java/org/eclipse/collections/companykata/Customer.java
		DoubleList filtered_ = this.company.getMostRecentCustomer()
				.getOrders()
				.asLazy()
				.collectDouble(Order::getValue)
				.select(DoublePredicates.greaterThan(1.5))
				.toList();// ここをtoSortedList()にすることで昇順にソートされる。
		// 方法2
		// MutabvleList<Order> orders
		// MutableList<Double orderValue
		// MutableList<Double> filtered
		// 以上の3個の変数を用意し段階的に解いていく。
		MutableList<Order> orders = this.company.getMostRecentCustomer().getOrders();
		MutableList<Double> orderValues = orders.collect(Order::getValue);
		MutableList<Double> filtered = orderValues.select(Predicates.greaterThan(1.5));
		Assert.assertEquals(Lists.mutable.with(372.5, 1.75), filtered);

		//取得地が正しいかの検証
		Assert.assertEquals(DoubleLists.mutable.with(372.5, 1.75), filtered_);
		//戻り値の型が指定されたとおりになっているかの検証。
		Verify.assertInstanceOf(MutableList.class, this.company.getMostRecentCustomer().getOrders());
		// フィールドの値をmutableListにすると値渡しでなく参照渡しをすることができた。
		// 理解は及んでいない。List型のArrayListは参照渡しできなさそうであるが、そのことに関して学習できていない。
		// TODO Javaの参照渡し、値渡しに関してもっとよく理解すること。
		this.company.getMostRecentCustomer().getOrders().add(null);
		//fieldがMutableListになっているかの検証。Not JCF
		Verify.assertContains("Don't return a copy from Customer.getOrders(). The field should be a MutableList.", null,
				this.company.getMostRecentCustomer().getOrders());
	}

	@Test
	public void filterOrders() {
		MutableList<Order> orders = this.company.getMostRecentCustomer().getOrders();
		/**
		 * Same exercise but don't use static utility - refactor the type of orders and {@link Customer#getOrders()}
		 * instead.
		 * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
		 */

		//order二回書かずに記述したかったけどわかりかねた。
		//TODO::メソッド参照。
		MutableList<Order> filtered = orders.
				select(order->order.getValue()>2.0);

		Assert.assertEquals(Lists.mutable.with(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders())),
				filtered);
		Verify.assertInstanceOf(MutableList.class, this.company.getMostRecentCustomer().getOrders());
		this.company.getMostRecentCustomer().getOrders().add(null);
		Verify.assertContains("Don't return a copy from Customer.getOrders(). The field should be a MutableList.", null,
				this.company.getMostRecentCustomer().getOrders());
	}
}
