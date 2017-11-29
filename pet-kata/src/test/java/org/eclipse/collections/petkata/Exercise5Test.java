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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise5Test extends PetDomainForKata {
	/*
	 * PartitionMutableListを使用する。
	 * petありとpetなしに分ける。
	 */

	@Test
	public void partitionPetAndNonPetPeople() {
		PartitionMutableList<Person> partitionMutableList = null;
		partitionMutableList = this.people.partition(person-> person.isPetPerson());
		//TODO::どういう流れかわからないが、predicateで指定した関数でpositive とnegativeに分割している。
		//おそらく、入れるときにポジティブとネガティブにいれてるのだと思うが、ソース見ただけではわからない。
		//デバッグすると流れはわかるはず。
		Verify.assertSize(7, partitionMutableList.getSelected());
		Verify.assertSize(1, partitionMutableList.getRejected());
	}

	/*
	 * 何かしたいときに参照する場所は
	 * Richiteratble！
	 * maxBy
	 * minBy
	 * などがある。
	 * もし、データがなかったらnullがくる。
	 * null対策はしないといけないなと。
	 */

	@Test
	public void getOldestPet() {
		Pet oldestPet = null;
		//デバッグしてみると、順次、値を比較してくれている。
		oldestPet = this.people.flatCollect(Person::getPets).maxBy(pet->pet.getAge());
		Assert.assertEquals(PetType.DOG, oldestPet.getType());
		Assert.assertEquals(4, oldestPet.getAge());
	}

	/*
	 *
	 */
	@Test
	public void getAveragePetAge() {
		double averagePetAge = 0;
		Assert.assertEquals(1.8888888888888888, averagePetAge, 0.00001);
	}

	@Test
	public void addPetAgesToExistingSet() {
		// Hint: Use petAges as a target collection
		MutableIntSet petAges = IntSets.mutable.with(5);
		Assert.assertEquals(IntSets.mutable.with(1, 2, 3, 4, 5), petAges);
	}

	@Test
	public void refactorToEclipseCollections() {
		// Replace List and ArrayList with Eclipse Collections types
		List<Person> people = new ArrayList<>();
		people.add(new Person("Mary", "Smith").addPet(PetType.CAT, "Tabby", 2));
		people.add(new Person("Bob", "Smith")
				.addPet(PetType.CAT, "Dolly", 3)
				.addPet(PetType.DOG, "Spot", 2));
		people.add(new Person("Ted", "Smith").addPet(PetType.DOG, "Spike", 4));
		people.add(new Person("Jake", "Snake").addPet(PetType.SNAKE, "Serpy", 1));
		people.add(new Person("Barry", "Bird").addPet(PetType.BIRD, "Tweety", 2));
		people.add(new Person("Terry", "Turtle").addPet(PetType.TURTLE, "Speedy", 1));
		people.add(new Person("Harry", "Hamster")
				.addPet(PetType.HAMSTER, "Fuzzy", 1)
				.addPet(PetType.HAMSTER, "Wuzzy", 1));
		people.add(new Person("John", "Doe"));

		// Replace Set and HashSet with Eclipse Collections types
		Set<Integer> petAges = new HashSet<>();
		for (Person person : people) {
			for (Pet pet : person.getPets()) {
				petAges.add(pet.getAge());
			}
		}

		// extra bonus - convert to a primitive collection
		Assert.assertEquals("Extra Credit - convert to a primitive collection", Sets.mutable.with(1, 2, 3, 4), petAges);

		Assert.fail("Refactor to Eclipse Collections");
	}
}
