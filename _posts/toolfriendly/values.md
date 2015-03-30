---
layout: post
title: "Values, values everywhere"
date: 2015-03-30 11:00:00
category: toolfriendly
tags: [toolfreidnly, scala]
featured_image: /images/cover.jpg
---

# Values, values everywhere

The basic feature of any launge is value. It allows us to reuse result of computation, named or many more. So how to use them to make our code more toolfriendly?

Let's look at "real-world" example. We have to format and pretty print polish mobile numbers. Like this:

```scala
//takes number like 123456789 and format it as +48 123 456 789 
def format(nr: String) = nr.sliding(3).mkString("+48", " ", "")  
 
val numbers = Seq("+48730409203", "890890886", "700 440 675", "700 45 34 34")
```

Ok, it is trivial so 2 mins of coding and we end up with:

```scala
numbers.map(_.replaceAll(" ", "")).map(_.replace("+48", "")).map(format)
```

And lets assume that we have some problems with one of replace. So lets see how our numbers looks after first map.

And we can't do it. JVM store return values direcly on stack and JDI has no direct access to stack.

Solution? lets name what we get after each transformation. Then debugging or even reading is plasure :)

```scala
val withoutSpaces = numbers.map(_.replaceAll(" ", ""))
val withoutLocal = withoutSpaces.map(_.replace("+48", ""))
withoutLocal.map(format)
```
