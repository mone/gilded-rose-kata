Hi and welcome to team Gilded Rose.

As you know, we are a small inn with a prime location in a prominent city ran
by a friendly innkeeper named Allison.  We also buy and sell only the finest
goods. Unfortunately, our goods are constantly degrading in quality as they
approach their sell by date.

We have a system in place that updates our inventory for us. It was developed
by a no-nonsense type named Leeroy, who has moved on to new adventures. Your
task is to add the new feature to our system so that we can begin selling a
new category of items.

First an introduction to our system:

  - All items have a sell-in value which denotes the number of days we have to
    sell the item

  - All items have a quality value which denotes how valuable the item is

  - At the end of each day our system lowers both values for every item

Pretty simple, right? Well this is where it gets interesting:

  - Once the sell by date has passed, quality degrades twice as fast

  - The quality of an item is never negative

  - The quality of an item is never more than 50

The following Exceptions exist:

  - "Aged Brie" actually increases in quality the older it gets

  - "Sulfuras", being a legendary item, never has to be sold or decreases in
    quality. Its quality is 80 (above the standard max of 50) and it never alters.

  - "Backstage passes", like aged brie, increases in quality as it's sell-in
    value approaches; quality increases by 2 when there are 10 days or less
    and by 3 when there are 5 days or less but quality drops to 0 after the
    concert

We have recently signed a supplier of conjured items. This requires an update
to our system:

  - "Conjured" items degrade in quality twice as fast as normal items

Feel free to make any changes to the update-quality method and add any new code
as long as everything still works correctly. However, do not alter the item
function as that belongs to the goblin in the corner who will insta-rage and
one-shot you as he doesn't believe in shared code ownership.

## Stories

N1 Existing code should be tested

N2 Better item wrappers should be prepared to ease update implementation

N3 Code should be refactored to be more readable

N4 "Conjured" item category should be introduced

N5 We should talk to the customer to convince them to migrate from the name-based classification
to an explicit classification via dedicated property: the current system is way too fragile (a
simple typo will put an item in the wrong category)
