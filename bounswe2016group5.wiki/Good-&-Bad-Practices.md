## Good Practises in Software Engineering

### S.O.L.I.D.

* “**SOLID**” is actually a group of object-oriented design principles used by individual developers when writing code. Each letter in “**SOLID**” represents one of the principles, which are:

  * **S**ingle responsibility.
  * **O**pen-closed.
  * **L**iskov substitution.
  * **I**nterface segregation.
  * **D**ependency inversion.

* When applied together, these principles help a developer create code that is easy to maintain and extend over time – in other words, **the code is solid**.

### Automated Unit Testing

* A developer writes unit tests against the code and then executes those tests over and over again throughout the project to make sure the code continues working as expected.

* You get all sorts of benefit from using this practice!

  * First, developers are **a lot more confident** they haven’t broken anything when making changes to code so it takes them less time to make those changes.
  * Second, automated unit testing helps you **find problems early** when it is easier to fix them. You also can use them to perform ongoing **regression tests** so you don’t introduce new problems down the line.
  * Third, they also provide “living documentation” for the system, which helps a new developer **ramp up quickly**.

### Continuous Integration

* The practice of continuous integration does exactly what its name implies: integrate code **early and often** so you avoid problems trying to integrate code later in the project.

* Continuous integration works like this: a developer checking new code into the team’s source code control repository triggers the continuous integration process. The continuous integration process runs other processes, like the build process, tests, and code analysis. Continuous integration detects any problems with the code quickly and provides feedback so the developer can fix them before they become even bigger problems later.

[Source](https://www.excella.com/insights/best-software-engineering-practices)

## Bad Practises in Software Engineering

* Ambiguous communication
* Overwhelming complexity
* Insufficient testing
* Insufficient requirements management
* Undetected inconsistencies among requirements, designs and implementations
* Brittle architecture

[Source](https://www.cs.utexas.edu/~mitra/csSummer2014/cs312/lectures/bestPractices.html)


## A Different Approach: Principles Are Timeless Best Practices Are Fads

Most best practices do apply pretty broadly and are generally helpful in a large number of different contexts.

For example, it is considered a best practice to use a source control system and it doesn’t seem like there are many situations where this wouldn’t be the case.

So doesn’t that make it a concrete rule or a principle?

No, it is still too specific to be generally applied in all cases and the act of putting your code in source control does nothing to improve the quality of your software or software product. 

**If you were to blindly follow any best practice and not apply that best practice in a way that brings out the underlying principle, you would be very unlikely to actually receive any benefit.**

You see, **most best practices are actually derived from universally applicable principles that never change**.  That is why most best practices are good.

The problem is applying the best practice itself in no way assures the benefit of its underlying principle.

To put it plainly, there is something greater at work that makes it a good idea to check your code into a source control system.  It is entirely possible to follow the action, but completely miss the spirit of the action.

[Source](http://simpleprogrammer.com/2013/02/17/principles-are-timeless-best-practices-are-fads/)