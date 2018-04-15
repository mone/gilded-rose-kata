package com.gildedrose

import com.gildedrose.ExtendedItem._
import org.scalatest.{FlatSpec, Matchers}

class ExtendedItemTest extends FlatSpec with Matchers {

  behavior of "ExtendedItem"

  it should "make Aged Brie into an GainQualityWithAgeItem" in {
    ExtendedItem(new Item("Aged Brie", 0, 0)) should be (
      GainQualityWithAgeItem("Aged Brie", 0, 0)
    )
  }

  it should "make Backstage passes to a TAFKAL80ETC concert into an BackstagePasses" in {
    ExtendedItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0)) should be (
      BackstagePass("Backstage passes to a TAFKAL80ETC concert", 0, 0)
    )
  }

  it should "make Sulfuras, Hand of Ragnaros into an LegendaryItem" in {
    ExtendedItem(new Item("Sulfuras, Hand of Ragnaros", 0, 0)) should be (
      LegendaryItem("Sulfuras, Hand of Ragnaros", 0)
    )
  }

  it should "make conjured item into a ConjuredItem" in {
    ExtendedItem(new Item("Conjured whatever", 0, 0)) should be (
      ConjuredItem("Conjured whatever", 0, 0)
    )
  }

  it should "make generic item into a GenericItem" in {
    ExtendedItem(new Item("whatever", 0, 0)) should be (
      GenericItem("whatever", 0, 0)
    )
  }

  behavior of "ExtendedItem.increaseQuality"

  it should "increase quality by 1" in {
    ExtendedItem.increaseQuality(0) should be (1)
    ExtendedItem.increaseQuality(1) should be (2)
    ExtendedItem.increaseQuality(49) should be (50)
  }

  it should "increase quality by specified amount" in {
    ExtendedItem.increaseQuality(0, 2) should be (2)
    ExtendedItem.increaseQuality(0, 50) should be (50)
    ExtendedItem.increaseQuality(1, 3) should be (4)
  }

  it should "not increase quality over 50" in {
    ExtendedItem.increaseQuality(50) should be (50)
    ExtendedItem.increaseQuality(49, 2) should be (50)
    ExtendedItem.increaseQuality(0, 51) should be (50)
  }

  behavior of "ExtendedItem.decreaseQuality"

  it should "decrease quality by 1" in {
    ExtendedItem.decreaseQuality(1) should be (0)
    ExtendedItem.decreaseQuality(2) should be (1)
    ExtendedItem.decreaseQuality(50) should be (49)
  }

  it should "decrease quality by specified amount" in {
    ExtendedItem.decreaseQuality(2, 2) should be (0)
    ExtendedItem.decreaseQuality(4, 3) should be (1)
  }

  it should "not decrease quality below 0" in {
    ExtendedItem.decreaseQuality(0) should be (0)
    ExtendedItem.decreaseQuality(2, 3) should be (0)
  }

  behavior of "GenericItem.updateQuality"

  it should "decrease quality and sellIn by 1" in {
    GenericItem("item", 2, 2).updateQuality should be (GenericItem("item", 1, 1))
  }

  it should "decrease quality twice when sell by date has passed" in {
    GenericItem("item", 0, 2).updateQuality should be (GenericItem("item", -1, 0))
  }

  behavior of "GainQualityWithAgeItem.updateQuality"

  it should "increase quality and decrease sellIn" in {
    GainQualityWithAgeItem("item", 2, 2).updateQuality should be (GainQualityWithAgeItem("item", 1, 3))
  }

  behavior of "BackstagePass.updateQuality"

  it should "increase quality and decrease sellIn when concert is more than 10 days away" in {
    BackstagePass("item", 11, 2).updateQuality should be (BackstagePass("item", 10, 3))
  }

  it should "increase quality twice when concert is more less than 10 days away" in {
    BackstagePass("item", 10, 2).updateQuality should be (BackstagePass("item", 9, 4))
  }

  it should "increase quality trice when concert is more less than 5 days away" in {
    BackstagePass("item", 5, 2).updateQuality should be (BackstagePass("item", 4, 5))
  }

  it should "set quality to 0 after expiration" in {
    BackstagePass("item", 0, 10).updateQuality should be (BackstagePass("item", -1, 0))
  }

  behavior of "LegendaryItem.updateQuality"

  it should "always stay the same" in {
    LegendaryItem("item", 0).updateQuality should be(LegendaryItem("item", 0))
  }

  behavior of "ConjuredItem.updateQuality"

  it should "decrease quality by 2 and sellIn by 1" in {
    ConjuredItem("item", 2, 2).updateQuality should be (ConjuredItem("item", 1, 0))
  }

  it should "decrease quality by 4 when sell by date has passed" in {
    ConjuredItem("item", 0, 4).updateQuality should be (ConjuredItem("item", -1, 0))
  }
}
