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

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * In the slides leading up to this exercise you should have learned about the following APIs.
 * <p/>
 * {@link Bag}<br>
 * {@link MutableBag}<br>
 * {@link MutableList#toBag()}<br>
 * <br>
 * {@link Multimap}<br>
 * {@link MutableList#groupBy(Function)}<br>
 * {@link MutableList#groupByEach(Function)}<br>
 * {@link Multimaps}<br>
 *
 * @see <a href="http://eclipse.github.io/eclipse-collections-kata/pet-kata/#/6">Exercise 3 Slides</a>
 */
public class Exercise3Test extends PetDomainForKata {

	/*
	 * getCountsByPetTypeでの課題で使用するメソッドは複数ある。
	 * １.toBagでflatCollectionをbagにする。
	 * 2.eachを使用し、以前に宣言したbagに追加する。
	 * 3.mutableCollectionのcountByでBagにする。
	 *
	 * 1,2は8.2.0で使用できる。
	 * 3.は９.0.0以降で使用できる。
	 *
	 * 今回このプロジェクトはeclipseCollectionsのgithubからcloneしてgradleプロジェクトとしてビルドした。
	 * build.gradleで定義されているのは、8.2.0である。
	 * mavenは9.0.0。
	 * gradleの設定を変えることで、@seeで提示されている、solveと同じ回答で記述することができる。
	 *
	 *
	 * そもそもBagとは。
	 * 買い物をするときのレシートに商品Aを何点購入するのような概念。
	 * もう少し抽象的に表現すると、itemの出現回数とitemをmap形式で重複なしで格納するための形式。
	 * itemがkeyとなる。
	 *
	 * 様々な表現ができるが、どれが適切化までは判断できていない。
	 */

	@Test
	public void getCountsByPetType() {
		// 必要な宣言－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		MutableList<PetType> petTypes = this.people.flatCollect(Person::getPets).collect(Pet::getType);

		// EC以前の記述－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Do you recognize this pattern?
		MutableMap<PetType, Integer> petTypeCounts = Maps.mutable.empty();
		for (PetType petType : petTypes) {
			Integer count = petTypeCounts.get(petType);
			if (count == null) {
				count = 0;
			}
			petTypeCounts.put(petType, count + 1);
		}

		Assert.assertEquals(Integer.valueOf(2), petTypeCounts.get(PetType.CAT));
		Assert.assertEquals(Integer.valueOf(2), petTypeCounts.get(PetType.DOG));
		Assert.assertEquals(Integer.valueOf(2), petTypeCounts.get(PetType.HAMSTER));
		Assert.assertEquals(Integer.valueOf(1), petTypeCounts.get(PetType.SNAKE));
		Assert.assertEquals(Integer.valueOf(1), petTypeCounts.get(PetType.TURTLE));
		Assert.assertEquals(Integer.valueOf(1), petTypeCounts.get(PetType.BIRD));

		// Bagを用いた記述－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Hint: use the appropriate method on this.people to create a Bag<PetType>
		// 下記の書き方の場合finalが必要になる。下記の書き方の場合Bag<PetType>で記述できない。
		// MutableBagはCollectionを継承。子 MutableBagIterable<T>→MutableCollection<T>→Collections<T> 親
		final MutableBag<PetType> counts = Bags.mutable.empty();
		petTypes.each(petType -> counts.add(petType));// countsは上の方でflattenされています。

		// 以降の書き方の場合finalは不要。また、以下の書き方の場合Bag<PetType>でも可
		MutableBag<PetType> counts_m = this.people.flatCollect(Person::getPets).collect(Pet::getType).toBag();
		;
		// EclipseCollections_8.2非対応 9.0.0から以下の書き方に対応。
		MutableBag<PetType> counts_n = this.people.flatCollect(Person::getPets).countBy(Pet::getType);

		Bag<PetType> counts_o = this.people.flatCollect(Person::getPets).collect(Pet::getType).toBag();
		counts_o = this.people.flatCollect(Person::getPets).countBy(Pet::getType);

		Assert.assertEquals(2, counts.occurrencesOf(PetType.CAT));
		Assert.assertEquals(2, counts.occurrencesOf(PetType.DOG));
		Assert.assertEquals(2, counts.occurrencesOf(PetType.HAMSTER));
		Assert.assertEquals(1, counts.occurrencesOf(PetType.SNAKE));
		Assert.assertEquals(1, counts.occurrencesOf(PetType.TURTLE));
		Assert.assertEquals(1, counts.occurrencesOf(PetType.BIRD));

		// －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	}

	/*
	 *getPeopleByLastName
	 *LastNameで収集し重複を除去せずをkeyでgroupingする。
	 *MultiMapの商法は上記の通りであるが、いまいち使用状況の想像がつかない。
	 *グルーピングしたいときに用いる様子がわかってきた。
	 *
	 */

	@Test
	public void getPeopleByLastName() {

		// EC以前の記述－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Do you recognize this pattern?
		MutableMap<String, MutableList<Person>> lastNamesToPeople = Maps.mutable.empty();
		for (Person person : this.people) {
			String lastName = person.getLastName();
			MutableList<Person> peopleWithLastName = lastNamesToPeople.get(lastName);
			if (peopleWithLastName == null) {
				peopleWithLastName = Lists.mutable.empty();
				lastNamesToPeople.put(lastName, peopleWithLastName);
			}
			peopleWithLastName.add(person);
		}
		Verify.assertIterableSize(3, lastNamesToPeople.get("Smith"));

		// MutiMapを用いた記述－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		// Hint: use the appropriate method on this.people to create a Multimap<String, Person>
		Multimap<String, Person> byLastNameMultimap = null;
		byLastNameMultimap = this.people.groupBy(person->person.getLastName());//lambda
		//or
		byLastNameMultimap = this.people.groupBy(Person::getLastName);//method reference
		//or
		final MutableMultimap<String, Person> byLastNameMultimap_ = Multimaps.mutable.list.empty();
		this.people.each(person -> byLastNameMultimap_.put(person.getLastName(), person));
		//or
		MutableListMultimap<String, Person> byLastNameMultimap__ = null;

		Verify.assertIterableSize(3, byLastNameMultimap.get("Smith"));
		// －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

	}

	@Test
	public void getPeopleByTheirPets() {
		// Do you recognize this pattern?
		MutableMap<PetType, MutableSet<Person>> peopleByPetType = Maps.mutable.empty();

		for (Person person : this.people) {
			MutableList<Pet> pets = person.getPets();
			for (Pet pet : pets) {
				PetType petType = pet.getType();
				MutableSet<Person> peopleWithPetType = peopleByPetType.get(petType);
				if (peopleWithPetType == null) {
					peopleWithPetType = Sets.mutable.empty();
					peopleByPetType.put(petType, peopleWithPetType);
				}
				peopleWithPetType.add(person);
			}
		}

		Verify.assertIterableSize(2, peopleByPetType.get(PetType.CAT));
		Verify.assertIterableSize(2, peopleByPetType.get(PetType.DOG));
		Verify.assertIterableSize(1, peopleByPetType.get(PetType.HAMSTER));
		Verify.assertIterableSize(1, peopleByPetType.get(PetType.TURTLE));
		Verify.assertIterableSize(1, peopleByPetType.get(PetType.BIRD));
		Verify.assertIterableSize(1, peopleByPetType.get(PetType.SNAKE));

		// Hint: use the appropriate method on this.people with a target collection to create a MutableSetMultimap<String, Person>
		// Hint: this.people is a MutableList, so it will return a MutableListMultimap without a target collection
		MutableSetMultimap<PetType, Person> multimap = null;

		Verify.assertIterableSize(2, multimap.get(PetType.CAT));
		Verify.assertIterableSize(2, multimap.get(PetType.DOG));
		Verify.assertIterableSize(1, multimap.get(PetType.HAMSTER));
		Verify.assertIterableSize(1, multimap.get(PetType.TURTLE));
		Verify.assertIterableSize(1, multimap.get(PetType.BIRD));
		Verify.assertIterableSize(1, multimap.get(PetType.SNAKE));
	}
}
