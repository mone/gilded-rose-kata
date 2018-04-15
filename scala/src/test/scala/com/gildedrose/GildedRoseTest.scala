package com.gildedrose

import org.scalatest._

class GildedRoseTest  extends FlatSpec with Matchers {

  behavior of "GildedRose"
  behavior of "GildedRose.updateQuality"

  it should "decrease quality and sell-in by 1 for a generic item" in {
    val items = Array[Item](new Item("foo", 2, 2))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (1)
    app.items(0).quality should equal (1)
  }

  it should "decrease quality twice when sell by date has passed" in {
    val items = Array[Item](new Item("foo", 0, 2))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (-1)
    app.items(0).quality should equal (0)
  }

  it should "not decrease quality below 0" in {
    val items = Array[Item](new Item("foo", 0, 0))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (-1)
    app.items(0).quality should equal (0)
  }

  it should "increase Aged Brie quality as it ages" in {
    val items = Array[Item](new Item("Aged Brie", 2, 2))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (1)
    app.items(0).quality should equal (3)
  }

  it should "increase Backstage passes quality as it ages and concert is more than 10 days away" in {
    val items = Array[Item](new Item("Backstage passes to a TAFKAL80ETC concert", 12, 2))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (11)
    app.items(0).quality should equal (3)
  }

  it should "increase Backstage passes twice if there are only 10 days remaining" in {
    val items = Array[Item](new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (9)
    app.items(0).quality should equal (4)
  }

  it should "increase Backstage passes trice if there are only 5 days remaining" in {
    val items = Array[Item](new Item("Backstage passes to a TAFKAL80ETC concert", 5, 2))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (4)
    app.items(0).quality should equal (5)
  }

  it should "set Backstage passes quality to 0 after expiration" in {
    val items = Array[Item](new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (-1)
    app.items(0).quality should equal (0)
  }

  it should "not increase quality over 50" in {
    val items = Array[Item](
      new Item("Aged Brie", 2, 50),
      new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50)
    )

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).quality should equal (50)
    app.items(1).quality should equal (50)
  }

  it should "keep Sulfuras quality to 80 and should not decrease its sellin" in {
    val items = Array[Item](new Item("Sulfuras, Hand of Ragnaros", 0, 10))

    val app = new GildedRose(items)
    app.updateQuality()

    app.items(0).sellIn should equal (0)
    app.items(0).quality should equal (80)
  }

}