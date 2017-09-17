# CoinMarketBehaviour
Compares cryptocurrencies and sorts them according to their market behaviour
This program tells the user which cryptocurrencies are alike (or not alike), given the list of currencies that exist on Bittrex exchange.
The end result are the names and values of currencies, sorted. The closer currencies on the list mean that they were behaving in some similar way over the period of the past 7 days. You can use this program to analyze the behaviour to have a better idea about what to invest in.


How to run?
You must issue a maven command running the main java file, or do it from IntelliJIDEA.


How does it work?
1. First, all 7-day images are downloaded from the coinmarketcap as the data representing the behaviour during the past 7 days.
2. For each image, the next image from the list is compressed together into one ZIP file, thus obtaining a multi-dimensional array representing where the image exists on the n-dimensional cartesian plane. The ZIP compression is used to represent the similarity of the data. Similar images would yield a smaller file, while other images would give bigger files, thus this data can be used to have a relationship-model within a Cartesian plane.
3. For each coin, the distances are added together, representing a total distance from each other coin.
4. The distances are sorted and displayed as an end result.


