/*
 * Copyright (c) 2017 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.petkata;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * In the slides leading up to this exercise you should have learned about the
 * following APIs.
 * <p/>
 * {@link MutableList#collect(Function)}<br>
 * {@link MutableList#select(Predicate)}<br>
 *
 * @see <a href=
 *      "http://eclipse.github.io/eclipse-collections-kata/pet-kata/#/2">Exercise
 *      1 Slides</a>
 */
public class Exercise1Test extends PetDomainForKata {
	@Test
	public void getFirstNamesOfAllPeople() {
		// Replace null, with a transformation method on MutableList.
		MutableList<String> firstNames = null; // this.people...
		//前提：PetDomainForKataの無名クラスを@Test実行に初期化。
		//無名クラスが持つMutableList<Person>型peopleが持つPersonクラスのfirstNameを集めたmutableなリストを生成する。
		// interface MutableList<T>クラスがもつメソッドにcollectがある。
		//   @Override
	    //<V> MutableList<V> collect(Function<? super T, ? extends V> function);
		//collectはfunctionが設定されていない。interface内を実装することでラムダ式になったりメソッド参照できる。
		//上記メソッドは、V（Person）を継承しているのでPersonメソッドを参照することができる。
		//ラムダ式で記述する場合は、people内のエンティティpersonが順次処理されることが推測できるので、
		//型は定義せず、perosn->を渡してpersonのメソッドを参照して実行することができる。
		//戻り値の読み方がわからず困っている。
		firstNames = this.people.collect(Person::getFirstName);//メソッド参照
		//or
		firstNames = this.people.collect(person->person.getFirstName());//ラムダ式
		MutableList<String> expectedFirstNames = Lists.mutable.with("Mary", "Bob", "Ted", "Jake", "Barry", "Terry",
				"Harry", "John");
		Assert.assertEquals(expectedFirstNames, firstNames);
	}

	@Test
	public void getNamesOfMarySmithsPets() {
		Person person = this.getPersonNamed("Mary Smith");
		MutableList<Pet> pets = person.getPets();

		// Replace null, with a transformation method on MutableList.
		MutableList<String> names = null; // pets...

		Assert.assertEquals("Tabby", names.makeString());
	}

	@Test
	public void getPeopleWithCats() {
		// Replace null, with a positive filtering method on MutableList.
		MutableList<Person> peopleWithCats = null; // this.people...

		Verify.assertSize(2, peopleWithCats);
	}

	@Test
	public void getPeopleWithoutCats() {
		// Replace null, with a negative filtering method on MutableList.
		MutableList<Person> peopleWithoutCats = null; // this.people...

		Verify.assertSize(6, peopleWithoutCats);
	}
}
