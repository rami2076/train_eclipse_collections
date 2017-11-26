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
		// MutableList<Person> petPeople = this.people; // replace with only the
		// pet owners
		MutableList<Person> petPeople = this.people.select(person -> person.isPetPerson());
		Verify.assertSize(7, petPeople);
	}

	/*
	 * mutableList はflatCollectを RichIterableから継承し、オーバーライドしている。
	 * RichIterativeはflatCollectはオーバーロードしている。
	 * 1<V> RichIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);
	 * 2<V, R extends Collection<V>> R flatCollect(Function<? super T, ? extends Iterable<V>> function, R target);
	 * 1のみをオーバライドしているのがmutableList
	 * 今回は2を使用。
	 * 1も2もfunctionを利用している。
	 * functionにはfunctioniterativeのアノテーションが使用されており、指定されたVの型の値をgetterして返す。
	 * 上記のfunctionはEclipseCollectionsの独自クラスで規定されている。
	 * Tは特定クラスで、Vは特定クラスが持つcollectionの型と思われる。
	 * その値をV型でcollectionにして返す。この時、入れ子のcollectionができるが、
	 * その入れ子を解除してフラットなcollectionにしてくれるのがこのクラス。
	 * また1のoverrideは以下である。以下の場合mutablelistの型が指定されているが、
	 * 格納したいクラスはmutableSetである。
	 * そのため今回は2のメソッドを使用する。
	 * mutableList以外のcollectionを使用する場合にはこちらのメソッドを使用するということ。
	 *  @Override
	*<V> MutableList<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);
	 *
	 *Sets.mutable.empty()はsetの初期化記法。
	 *List, Set, Bag, Map, Multimap等、すべてのコンテナに対してファクトリメソッドが存在（of(), with()どちら でも同様の初期化が可能）
	 *MutableList<String> mutableList = Lists.mutable.of("One", "One", "Two", "Three");
	 *MutableList<String> mutableList = Lists.mutable.with("One", "One", "Two", "Three");
	*MutableSet<String> mutableSet = Sets.mutable.of("One", "One", "Two", "Three");
	*MutableBag<String> mutableBag = Bags.mutable.of("One", "One", "Two", "Three");
	*MutableMap<String, String> mutableMap = Maps.mutable.of("key1", "value1", "key2", "value2", "key3", "value3");
	*Multimap<String, String> multimapWithList = Multimaps.mutable.list.of("key1", "value1-1", "key1", "value1-2", "key2",
	*"value2-1");
	*
	*空のコレクションを作るにはempty(), of(), with()どれでも可
	*MutableList<String> mutableListWithBlank = Lists.mutable.empty();
	*MutableList<String> mutableListWithBlank = Lists.mutable.of();
	*MutableList<String> mutableListWithBlank = Lists.mutable.with();
	 *
	 *すべてのプリミティブコンテナに対してもファクトリメソッドが存在
	MutableIntList intList = IntLists.mutable.of(1, 2, 3);
	MutableLongList longList = LongLists.mutable.of(1L, 2L, 3L);
	MutableCharList charList = CharLists.mutable.of('a', 'b', 'c');
	MutableShortList shortList = ShortLists.mutable.of((short)1, (short)2, (short)3);
	MutableByteList byteList = ByteLists.mutable.of((byte)1, (byte)2, (byte)3);
	MutableBooleanList booleanList = BooleanLists.mutable.of(true, false);
	MutableFloatList floatList = FloatLists.mutable.of(1.0f, 2.0f, 3.0f);
	MutableDoubleList doubleList = DoubleLists.mutable.of(1.0, 2.0, 3.0);
	IntIntervalを使うと範囲指定したintのコレクションを作成することが可能
	IntInterval oneTo10 = IntInterval.fromTo(1, 10);// 1から10までのint
	// [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
	IntInterval oneTo10By3 = IntInterval.fromToBy(1, 10, 3); // 1から10まで3ずつ増分したint
	// [1, 4, 7, 10]
	IntInterval oddsFrom1To10 = IntInterval.oddsFromTo(1, 10); // 1から10までの奇数
	// [1, 3, 5, 7, 9]
	IntInterval evensFrom1To10 = IntInterval.evensFromTo(1, 10); // 1から10までの偶数
	// [2, 4, 6, 8, 10]
	 *reffer
	 *http://www.goldmansachs.com/gs-collections/documents/2015-11-28_JJUG_CCC.pdf
	 *
	 *SetはJavaが持っているcollectionの一つ。
	 *重複がない場合に追加する。
	 *EclipseCollectionsのSetはjava.util.*のsetを継承しているので同じようにエラーを出さずに追加してくれる。
	 *
	 * Same as flatCollect, only the results are collected into the target collection.
	 *
	 * @param function The {@link Function} to apply
	 * @param target   The collection into which results should be added.
	 * @return {@code target}, which will contain a flattened collection of results produced by applying the given {@code function}
	 * @see #flatCollect(Function)
	 *
	//<V, R extends Collection<V>> R flatCollect(Function<? super T, ? extends Iterable<V>> function, R target);
	 *
	 *
	 *
	 * このテストテストでは、もう一つ、メソッドが登場している。
	 * Sets.mutable.with(...)だ。
	 * Setの初期化がなされている。
	 * withとofの二つのメソッドが用意されているが違いが見当たらない。
	 * 文字数がofの方が少ない。
	 *
	 */

	@Test
	public void getAllPetsOfAllPeople() {
		Function<Person, Iterable<PetType>> function = person -> person.getPetTypes();
		MutableSet<PetType> petTypes = this.people.flatCollect(function, Sets.mutable.empty());
		// or
		petTypes = this.people.flatCollect(function).toSet();
		Assert.assertEquals(
				Sets.mutable.with(PetType.CAT, PetType.DOG, PetType.TURTLE, PetType.HAMSTER, PetType.BIRD,
						PetType.SNAKE),
				petTypes);
	}

	/*
	 * ここではmutableListの初期化を行っている。
	 * mutableListの初期化は以下のように記述
	 * Lists.mutable.with(...)
	 * ofメソッドもあるが、こちらも違いは文字数のみ。
	 */

	@Test
	public void getFirstNamesOfAllPeople() {
		// MutableList<String> firstNames = null; // Transform this.people into a
		// list of first names
		MutableList<String> firstNames = this.people.collect(Person::getFirstName);
		Assert.assertEquals(
				Lists.mutable.with("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John"),
				firstNames);
	}

	/*
	 * doAnyPeopleHaveCatsのrefactoringを求めるテスト。
	 * 猫を飼っている人はいるかというテスト
	 *
	 * selectをメソッド参照で書く際に記述方法
	 *
	 * selectWith関数のtrain
	 *
	 * method referenceの書き方登場。
	 * 引数が二つある場合に記述するに使用する。
	 *
	 * 下記がそれであるが、なかなか難解。
	 * Predicate2で指定している箇所でPを使用する。
	 * BiPredicate<T1, T2>を継承している。
	 * 上記は引数が2ある場合に用いられる。メソッド参照を行うためのクラス。
	 * 難解である。
	 *
	 * Returns true if the predicate evaluates to true for any element of the collection, or return false.
	 * Returns false if the collection is empty.
	 *
	 * @since 5.0

	<P> boolean anySatisfyWith(Predicate2<? super T, ? super P> predicate, P parameter);
	 */

	@Test
	public void doAnyPeopleHaveCatsRefactor() {
		boolean peopleHaveCatsLambda = this.people.anySatisfy(person -> person.hasPet(PetType.CAT));
		Assert.assertTrue(peopleHaveCatsLambda);

		// use method reference, NOT lambdas, to solve the problem below
		boolean peopleHaveCatsMethodRef = false;
		peopleHaveCatsMethodRef = this.people.anySatisfyWith(Person::hasPet, PetType.CAT);
		Assert.assertTrue(peopleHaveCatsMethodRef);
	}

	/*
	 * doAllPeopleHaveCatsのrefactoringを行ったもの。
	 * method参照で記述できるallSatisfyWith methodのテスト
	 * 上のとほぼ同じ。
	 *
	 */

	@Test
	public void doAllPeopleHaveCatsRefactor() {
		boolean peopleHaveCatsLambda = this.people.allSatisfy(person -> person.hasPet(PetType.CAT));
		Assert.assertFalse(peopleHaveCatsLambda);

		// use method reference, NOT lambdas, to solve the problem below
		boolean peopleHaveCatsMethodRef = true;
		peopleHaveCatsMethodRef = this.people.allSatisfyWith(Person::hasPet, PetType.CAT);
		Assert.assertFalse(peopleHaveCatsMethodRef);
	}

	/*
	 *  getPeopleWithCats のrefactoring
	 *  method referenceで記述。
	 *  猫を飼っている飼い主をのリスト返す。
	 */

	@Test
	public void getPeopleWithCatsRefactor() {
		// use method reference, NOT lambdas, to solve the problem below
		MutableList<Person> peopleWithCatsMethodRef = null;
		peopleWithCatsMethodRef = this.people.selectWith(Person::hasPet,PetType.CAT);
		Verify.assertSize(2, peopleWithCatsMethodRef);
	}

	@Test
	public void getPeopleWithoutCatsRefactor() {
		// use method reference, NOT lambdas, to solve the problem below
		MutableList<Person> peopleWithoutCatsMethodRef = null;
		Verify.assertSize(6, peopleWithoutCatsMethodRef);
	}
}
