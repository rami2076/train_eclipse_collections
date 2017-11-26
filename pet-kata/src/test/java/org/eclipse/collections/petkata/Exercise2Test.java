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
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * In the slides leading up to this exercise you should have learned about the following APIs.
 * <p/>
 * {@link MutableList#flatCollect(Function)}<br>
 * {@link MutableList#select(Predicate)}<br>
 * {@link MutableList#reject(Predicate)}<br>
 * {@link MutableList#count(Predicate)}<br>
 * {@link MutableList#detect(Predicate)}<br>
 * {@link MutableList#anySatisfy(Predicate)}<br>
 * {@link MutableList#allSatisfy(Predicate)}<br>
 * {@link MutableList#noneSatisfy(Predicate)}<br>
 * <br>
 * You should have also learned about the following methods as well.<br>
 * <br>
 * {@link MutableList#selectWith(Predicate2, Object)}<br>
 * {@link MutableList#rejectWith(Predicate2, Object)}<br>
 * {@link MutableList#countWith(Predicate2, Object)}<br>
 * {@link MutableList#detectWith(Predicate2, Object)}<br>
 * {@link MutableList#anySatisfyWith(Predicate2, Object)}<br>
 * {@link MutableList#allSatisfyWith(Predicate2, Object)}<br>
 * {@link MutableList#noneSatisfyWith(Predicate2, Object)}<br>
 * <br>
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/pet-kata/#/4">Exercise 2 Slides</a>
 */
public class Exercise2Test extends PetDomainForKata {

	/*
	 * 猫をペットとしている人物が一人でもいるかのテスト
	 *
	 * Predicateのテスト内容を記述
	 * mutableListが持つanySatisfy()の引数predicateを渡す。
	 * 正確には、mutableListの継承親ー＞MutableCollection<T>ー＞RichIterable<T>が持つメソッド。
	 * 渡したcollection内をplidicateで規定された条件で判定し、一つでもTRUEだった場合にtrueを返す。
	 *
	 * 関数anySatisfyの関数
	 *
	 * RichIterableのAPI
	 *
	 * https://www.eclipse.org/collections/javadoc/7.0.0/org/eclipse/
	 * collections/api/RichIterable.html#anySatisfy-org.eclipse.collections.api.block.predicate.Predicate-
	 *
	 * Returns true if the predicate evaluates to true for any element of the iterable.
	 * Returns false if the iterable is empty, or if no element returned true when evaluating the predicate.
	 */
	@Test
	public void doAnyPeopleHaveCats() {
		// Predicate<Person> predicate = null; // replace null with a Predicate
		// lambda which checks for
		// PetType.CAT
		Predicate<Person> predicate = person -> person.hasPet(PetType.CAT);
		Assert.assertTrue(this.people.anySatisfy(predicate));
	}

	/*
	 * petを飼っていない人物がpeopleリストの中に一人いれば成功。
	 *
	 * テストを定義し、immutableListのallSatisfyで検証する。
	 * allSatisfyも引数がpredicateです。
	 * predicateが設定されていないnullだと普通にNullPointerExceptionがでる。
	 *
	 * 関数allSatisfyのtrain
	 *
	 * 少しずつ慣れてきた。
	 *
	 * java.lang.NullPointerException
	at org.eclipse.collections.impl.utility.internal.InternalArrayIterate.shortCircuit(InternalArrayIterate.java:679)
	at org.eclipse.collections.impl.utility.internal.InternalArrayIterate.allSatisfy(InternalArrayIterate.java:751)
	at org.eclipse.collections.impl.list.mutable.FastList.allSatisfy(FastList.java:1212)
	at org.eclipse.collections.petkata.Exercise2Test.doAllPeopleHavePets(Exercise2Test.java:90)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:678)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
	 */

	@Test
	public void doAllPeopleHavePets() {

		Predicate<Person> predicate = person -> person.isPetPerson();
		boolean result = true; // replace with a method call send to this.people
								// that checks if all people have pets
		result = this.people.allSatisfy(predicate);
		Assert.assertFalse(result);
	}

	/*
	 * mmutableListがRichIterableから継承したcountメソッド
	 * countメソッドも引数はPredicate
	 * Predicateで指定された判定を実行する。
	 *
	 * 関数countのtrain
	 */
	@Test
	public void howManyPeopleHaveCats() {
		int count = 0;
		Predicate<Person> predicate = person -> person.hasPet(PetType.CAT);
		count = this.people.count(predicate);
		Assert.assertEquals(2, count);
	}

	/*
	 * MarySmithを抽出する。
	 * firstName =Marry
	 * LastName = Smith
	 * に合致する要素を返す。
	 *
	 * detective関数を用いる。
	 * 最初に一致する。要素を返す関数。
	 * 要素に重複がない場合に使えるかな。
	 *
	 * detective関数も引数は、Predicateなので、条件つけてみる。
	 * lamdaの中も変数をしっかりみてくれる。
	 *
	 *詳細は理解していないが、predicateは名詞としては述語である。
	 *メソッド名なので、動詞である。意味は、断定する。（英英ではない）
	 *もともと、論理学のA is B. AはBである。のBを示す言葉としてアリストテレスが命名した言葉の様子。
	 *wikipediaがソースではっきりしたソースは示されておらず、ソースを追うこともできなかった。
	 *
	 *関数detectiveのtrain
	 */
	@Test
	public void findMarySmith() {
		String firtName = "Mary";
		String lastName = "Smith";
		Person result = null;

		// bigin test code
		// case prepare predicate method. predicate is https://ja.wikipedia.org/wiki/%E8%BF%B0%E8%AA%9E.
		Predicate<Person> predicate = person -> firtName.equals(person.getFirstName())
				&& lastName.equals(person.getLastName());
		result = this.people.detect(predicate);
		// case inline function . Less description than the above cases possible.
		result = this.people
				.detect(person -> firtName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
		// end test code.

		// test
		Assert.assertEquals("Mary", result.getFirstName());
		Assert.assertEquals("Smith", result.getLastName());
	}

	/*
	 * 内容は依然やった内容とほぼ同等。
	 * ペットを持つ人のリストを作成し人数が7であるかを測定。
	 * isPetPersonメソッドのテストを行う。
	 * Personクラスが持つ、isPetPaeronメソッドとMutableListクラスのselectメソッドを使用してリストを作成
	 *
	 *
	 *JunitのAssertを継承したorg.eclipse.collections.impl.test.Verifyクラスのテスト。
	 *collectionクラスに適したテストメソッドを持つ。
	 *
	 *関数assertSizeのtrain
	 */
	@Test
	public void getPeopleWithPets() {
		//MutableList<Person> petPeople = this.people; // replace with only the
														// pet owners
		MutableList<Person> petPeople = this.people.select(person->person.isPetPerson());
		Verify.assertSize(7, petPeople);
	}

	@Test
	public void getAllPetsOfAllPeople() {
		Function<Person, Iterable<PetType>> function = person -> person.getPetTypes();
		MutableSet<PetType> petTypes = null;
		Assert.assertEquals(
				Sets.mutable.with(PetType.CAT, PetType.DOG, PetType.TURTLE, PetType.HAMSTER, PetType.BIRD,
						PetType.SNAKE),
				petTypes);
	}

	@Test
	public void getFirstNamesOfAllPeople() {
		MutableList<String> firstNames = null; // Transform this.people into a
												// list of first names
		Assert.assertEquals(
				Lists.mutable.with("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John"),
				firstNames);
	}

	@Test
	public void doAnyPeopleHaveCatsRefactor() {
		boolean peopleHaveCatsLambda = this.people.anySatisfy(person -> person.hasPet(PetType.CAT));
		Assert.assertTrue(peopleHaveCatsLambda);

		// use method reference, NOT lambdas, to solve the problem below
		boolean peopleHaveCatsMethodRef = false;
		Assert.assertTrue(peopleHaveCatsMethodRef);
	}

	@Test
	public void doAllPeopleHaveCatsRefactor() {
		boolean peopleHaveCatsLambda = this.people.allSatisfy(person -> person.hasPet(PetType.CAT));
		Assert.assertFalse(peopleHaveCatsLambda);

		// use method reference, NOT lambdas, to solve the problem below
		boolean peopleHaveCatsMethodRef = true;
		Assert.assertFalse(peopleHaveCatsMethodRef);
	}

	@Test
	public void getPeopleWithCatsRefactor() {
		// use method reference, NOT lambdas, to solve the problem below
		MutableList<Person> peopleWithCatsMethodRef = null;
		Verify.assertSize(2, peopleWithCatsMethodRef);
	}

	@Test
	public void getPeopleWithoutCatsRefactor() {
		// use method reference, NOT lambdas, to solve the problem below
		MutableList<Person> peopleWithoutCatsMethodRef = null;
		Verify.assertSize(6, peopleWithoutCatsMethodRef);
	}
}
