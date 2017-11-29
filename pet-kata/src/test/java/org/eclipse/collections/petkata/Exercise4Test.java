/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.petkata;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.set.primitive.IntSet;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.Assert;
import org.junit.Test;

/**
 * In this set of tests, wherever you see .stream() replace it with an Eclipse Collections alternative.
 */
public class Exercise4Test extends PetDomainForKata {

	/*
	 *getAgeStatisticsOfPets
	 *  一意のペットの年齢のリスト
	 *ペットの年齢のリストの生成。
	 *flatcollectionでペットのcollectionを生成
	 *ペットの年齢をcollectionする
	 *collectでcollectionの変換
	 *
	 *ここでは、streamとEclipseCollectionsの比較を行う。
	 *streamの場合は、再利用できないため、リスト化してstreamにする手続きが必要になる。TODO::検証、必要。
	 *EclipseCollectionsの場合は再利用できるため、コードが簡易になる。そのことを確認してほしい。
	 *また以下の書き方以外の書き方の存在する。
	 */

	@Test
	public void getAgeStatisticsOfPets() {

		// Start Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// MutableIntListへの置き換えをやってみよう
		// ペットの年齢のリストの生成。
		// flatcollectionでペットのcollectionを生成
		// ペットの年齢をcollectionする
		// collectでcollectionの変換
		// Try to use a MutableIntList here instead
		// Hints: flatMap = flatCollect, map = collect, mapToInt = collectInt

		// before-------------------
		MutableList<Integer> petAges = this.people
				.stream()
				.flatMap(person -> person.getPets().stream())
				.map(pet -> pet.getAge())
				.collect(Collectors.toCollection(FastList::new));

		// after-------------------

		MutableIntList petAges_EC = this.people
				.flatCollect(Person::getPets)
				.collectInt(Pet::getAge);// .toList();//TODO::toList()にする意味がよくわからない。
		// TODO:: asLasy()を使った変換はまだ試していない。
		Long lon = petAges_EC.sum();
		Double dob = petAges_EC.average();

		// 上でsumとかを出せるが、IntArrayListのメソッドとして;だせるが。
		// 効率的でない。
		// IntSummaryStaticsの使用を推奨する。
		// すべて吐き出す場合。

		// otherwise-------------------
		// asLazy で LazyListIteratbleに変換される。
		// TODO::遅延反復の理解不足。
		RichIterable<Person> test = this.people.asLazy();

		MutableIntList petAges_EC_Otherwise = this.people.asLazy()
				.flatCollect(Person::getPets)
				.collectInt(Pet::getAge)
				.toList();
		test.each(System.out::println);
		// End Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// *******************

		// Start Section2－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// IntSetへの置き換えをやってみよう。
		// Try to use an IntSet here instead
		// before-------------------
		Set<Integer> uniqueAges = petAges.toSet();
		// after-------------------
		IntSet uniqueAges_EC = petAges_EC.toSet();
		uniqueAges.forEach(System.out::print);
		uniqueAges_EC.forEach(System.out::print);
		// Setに変換はできているが、値がuniqueAges_ECオブジェクト内のどこに格納されているか不明。

		// End Section2－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// *******************

		// Start Sections3－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// 簡単な統計情報を作成するクラスがJDK8に用意されている。
		// それがIntSummaryStatics
		// https://docs.oracle.com/javase/jp/8/docs/api/java/util/IntSummaryStatistics.html
		// ECのAPI MutableIntList.forEach()を使って表現してみよう。
		// IntSummaryStatistics is a class in JDK 8 - Try and use it with MutableIntList.forEach()
		// before-------------------
		IntSummaryStatistics stats = petAges.stream().mapToInt(i -> i).summaryStatistics();
		// after-------------------
		IntSummaryStatistics stats_EC = new IntSummaryStatistics();
		petAges_EC.forEach(stats_EC::accept);
		// End Section3－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// *******************

		// Start Section4 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// Set<Integer>とIntSetは同じか確認してみよう。
		// Is a Set<Integer> equal to an IntSet?
		// Hint: Try IntSets instead of Sets as the factory
		// before-------------------
		Assert.assertEquals(Sets.mutable.with(1, 2, 3, 4), uniqueAges);
		// after-------------------
		// TODO:: Set<Integer>とIntSetはどうも異なりそうである。
		// Assert.assertEquals(uniqueAges, uniqueAges_EC);
		// End Section4－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// *******************

		// Start Sections5－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// 最小値、最大値、平均をEclipseCollectionsのプリミティブなapiを活用して表現してみよう。
		// Try to leverage min, max, sum, average from the Eclipse Collections primitive api
		// before-------------------
		Assert.assertEquals(stats.getMin(), petAges.stream().mapToInt(i -> i).min().getAsInt());
		Assert.assertEquals(stats.getMax(), petAges.stream().mapToInt(i -> i).max().getAsInt());
		Assert.assertEquals(stats.getSum(), petAges.stream().mapToInt(i -> i).sum());
		Assert.assertEquals(stats.getAverage(), petAges.stream().mapToInt(i -> i).average().getAsDouble(), 0.0);
		Assert.assertEquals(stats.getCount(), petAges.size());
		// after-------------------

		Assert.assertEquals(stats_EC.getMin(), petAges_EC.min());
		Assert.assertEquals(stats_EC.getMax(), petAges_EC.max());
		Assert.assertEquals(stats_EC.getSum(), petAges_EC.sum());
		Assert.assertEquals(stats_EC.getAverage(), petAges_EC.average(), 0.0);// MEMO::比較する小数点以下の位の桁数の指定。
		Assert.assertEquals(stats_EC.getCount(), petAges_EC.size());
		// IntArrayListが持つsumなどのメソッドで簡易に計算することができる。
		// End Section5－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// *******************

		// Start Section6－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// 状態を満たすか確認する機能をEclipseCollectionsで実装してみよう。
		// Hint: Match = Satisfy
		// before-------------------
		Assert.assertTrue(petAges.stream().allMatch(i -> i > 0));
		Assert.assertFalse(petAges.stream().anyMatch(i -> i == 0));
		Assert.assertTrue(petAges.stream().noneMatch(i -> i < 0));
		// after-------------------
		Assert.assertTrue(petAges_EC.allSatisfy(i -> i > 0));
		Assert.assertFalse(petAges_EC.anySatisfy(i -> i == 0));
		Assert.assertTrue(petAges_EC.noneSatisfy(i -> i < 0));
		// End Section6 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// *******************

		// Start Section7－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// Don't forget to comment this out or delete it when you are done
		// Assert.fail("Refactor to Eclipse Collections");
		// End Section7 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	}

	/*
	 *
	 */

	@Test
	public void streamsToECRefactor1() {

		// Start Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// before-------------------
		// find Bob Smith
		Person person = this.people.stream()
				.filter(each -> each.named("Bob Smith"))
				.findFirst().get();
		// after-------------------
		Person person_EC = this.people.detectWith(Person::named, "Bob Smith");
		// or
		// TODO::eachを引数に渡している意味は少しわからない。personでもよさそうだが、、、
		// それぞれの名前を判別していくという意味合いか？
		Person person_EC_Otherwise = this.people.detect(each -> each.named("Bob Smith"));
		// End Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Start Section2－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// before-------------------
		// get Bob Smith's pets' names
		String names = person.getPets().stream()
				.map(Pet::getName)
				.collect(Collectors.joining(" & "));

		Assert.assertEquals("Dolly & Spot", names);

		// after-------------------
		String names_EC = person_EC.getPets().collect(Pet::getName).makeString(" & ");
		Assert.assertEquals("Dolly & Spot", names_EC);
		// or
		String names_EC_Otherwise = person_EC_Otherwise.getPets().collect(Pet::getName).makeString(" & ");
		Assert.assertEquals("Dolly & Spot", names_EC_Otherwise);

		//otherwise と思ったが、namesのほうは同じだった。
		//find Bob Smith
		final Person person_ =
		                this.people
		                        .select(each -> each.named("Bob Smith"))
		                        .getFirst();

		//get Bob Smith's pets' names
		final String names_ =
		                person_.getPets()
		                        .collect(Pet::getName)
		                        .makeString(" & ");

		// End Section2－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Don't forget to comment this out or delete it when you are done
		// Assert.fail("Refactor to Eclipse Collections");
	}

	@Test
	public void streamsToECRefactor2() {

		// Start Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Hint: Try to replace the Map<PetType, Long> with a Bag<PetType>
		// before-------------------
		Map<PetType, Long> countsStream = Collections.unmodifiableMap(
				this.people.stream()
						.flatMap(person -> person.getPets().stream())
						.collect(Collectors.groupingBy(Pet::getType,
								Collectors.counting())));
		Assert.assertEquals(Long.valueOf(2L), countsStream.get(PetType.CAT));
		Assert.assertEquals(Long.valueOf(2L), countsStream.get(PetType.DOG));
		Assert.assertEquals(Long.valueOf(2L), countsStream.get(PetType.HAMSTER));
		Assert.assertEquals(Long.valueOf(1L), countsStream.get(PetType.SNAKE));
		Assert.assertEquals(Long.valueOf(1L), countsStream.get(PetType.TURTLE));
		Assert.assertEquals(Long.valueOf(1L), countsStream.get(PetType.BIRD));
		// affter-------------------
		// getTypes自体がBagを生成するメソッドなのであまり学習になりにくい。
		// そのため、petの配列を用いた書き方をしていると思われる。
		Bag<PetType> countsStream_EC = this.people.flatCollect(person -> person.getPetTypes(), Bags.mutable.empty());
		// こちらの書き方が正しい。記述なのでこちらのみテストする。
		Bag<PetType> countsStream_EC_Othewise = this.people.flatCollect(Person::getPets).countBy(Pet::getType);

		//otherwise
		final Bag<PetType> countsStream_ =
                this.people
                        .flatCollect(person -> person.getPets())
                        .collect(pet -> pet.getType())
                        .toBag();
		// メソッド assertEquals(Object, Object) は型 Assert であいまいです。
		// といわれたので、書き換えを行った。
		// overloadでvalueOf methodが被っている箇所があるらしい。そのため型が合間になっているらしいとのこと。
		// TODO::mean reaserch
		Assert.assertEquals(2, countsStream_EC_Othewise.occurrencesOf(PetType.CAT));
		Assert.assertEquals(2L, countsStream_EC_Othewise.occurrencesOf(PetType.DOG));
		Assert.assertEquals(2, countsStream_EC_Othewise.occurrencesOf(PetType.HAMSTER));
		Assert.assertEquals(1, countsStream_EC_Othewise.occurrencesOf(PetType.SNAKE));
		Assert.assertEquals(1, countsStream_EC_Othewise.occurrencesOf(PetType.TURTLE));
		// Assert.assertEquals(Long.valueOf(1L), countsStream_EC_Othewise.occurrencesOf(PetType.BIRD));
		Assert.assertEquals(1L, countsStream_EC_Othewise.occurrencesOf(PetType.BIRD));

		// End Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Don't forget to comment this out or delete it when you are done
		// Assert.fail("Refactor to Eclipse Collections");
	}

	/**
	 * The purpose of this test is to determine the top 3 pet types.
	 */
	@Test
	public void streamsToECRefactor3() {

		// Start Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// before-------------------
		// Hint: The result of groupingBy/counting can almost always be replaced by a Bag
		// Hint: Look for the API on Bag that might return the top 3 pet types
		List<Map.Entry<PetType, Long>> favoritesStream = this.people.stream()
				.flatMap(p -> p.getPets().stream())
				.collect(Collectors.groupingBy(Pet::getType, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted(Comparator.comparingLong(e -> -e.getValue()))
				.limit(3)
				.collect(Collectors.toList());
		Verify.assertSize(3, favoritesStream);
		Verify.assertContains(new AbstractMap.SimpleEntry<>(PetType.CAT, Long.valueOf(2)), favoritesStream);
		Verify.assertContains(new AbstractMap.SimpleEntry<>(PetType.DOG, Long.valueOf(2)), favoritesStream);
		Verify.assertContains(new AbstractMap.SimpleEntry<>(PetType.HAMSTER, Long.valueOf(2)), favoritesStream);
		// affter-------------------
		Bag<PetType> favoritesStream_EC = this.people.flatCollect(Person::getPets).countBy(Pet::getType);
		MutableList<ObjectIntPair<PetType>> favorites = (MutableList<ObjectIntPair<PetType>>) favoritesStream_EC
				.topOccurrences(3);
		// or
		MutableList<ObjectIntPair<PetType>> favorites_EC = this.people
				.flatCollect(Person::getPets)
				.countBy(Pet::getType)
				.topOccurrences(3);
		// otherwise
		MutableList<ObjectIntPair<PetType>> favorites_ = this.people
				.asLazy()
				.flatCollect(Person::getPets)
				.countBy(Pet::getType)
				.topOccurrences(3)
				.toList();
		// 解答にはtoListが抜けている。

		// 遅延反復の配列にすると型を充てることができない?
		// .toListを割り当てることで、型が一致リストまで生成され問題解消。
		Verify.assertSize(3, favorites);
		Verify.assertContains(PrimitiveTuples.pair(PetType.CAT, 2), favorites_);
		Verify.assertContains(PrimitiveTuples.pair(PetType.DOG, 2), favorites_);
		Verify.assertContains(PrimitiveTuples.pair(PetType.HAMSTER, 2), favorites_);

		// 遅延反復の配列にすると型を充てることができない
		Verify.assertSize(3, favorites);
		Verify.assertContains(PrimitiveTuples.pair(PetType.CAT, 2), favorites_EC);
		Verify.assertContains(PrimitiveTuples.pair(PetType.DOG, 2), favorites_EC);
		Verify.assertContains(PrimitiveTuples.pair(PetType.HAMSTER, 2), favorites_EC);

		Verify.assertSize(3, favorites_);
		Verify.assertContains(PrimitiveTuples.pair(PetType.CAT, 2), favorites);
		Verify.assertContains(PrimitiveTuples.pair(PetType.DOG, 2), favorites);
		Verify.assertContains(PrimitiveTuples.pair(PetType.HAMSTER, 2), favorites);

		// otherwise
		List<ObjectIntPair<PetType>> favoritesStream_ = this.people
				.flatCollect(p -> p.getPets())
				.collect(pet -> pet.getType())
				.toBag()
				.topOccurrences(3);
		// TODO::finalを付けた時とつけないときの違いが判らない
		final List<ObjectIntPair<PetType>> favoritesStream__ = this.people
				.flatCollect(p -> p.getPets())
				.collect(pet -> pet.getType())
				.toBag()
				.topOccurrences(3);
		//countByはより簡易に自由度のある集約ができる。
		MutableList<ObjectIntPair<PetType>> favoritesStream___ = this.people
				.flatCollect(p -> p.getPets())
				.collect(pet -> pet.getType())
				.toBag()
				.topOccurrences(3);

		Verify.assertSize(3, favoritesStream_);
		Verify.assertContains(PrimitiveTuples.pair(PetType.CAT, 2), favoritesStream_);
		Verify.assertContains(PrimitiveTuples.pair(PetType.DOG, 2), favoritesStream_);
		Verify.assertContains(PrimitiveTuples.pair(PetType.HAMSTER, 2), favoritesStream_);

		// End Section1－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Don't forget to comment this out or delete it when you are done
		// Assert.fail("Refactor to Eclipse Collections");
	}
}
