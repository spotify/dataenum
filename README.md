# DataEnum

DataEnum allows you to work with [algebraic data types](https://en.wikipedia.org/wiki/Algebraic_data_type) in Java.

You can think of it as an enum where every individual value can have different data associated with it.

## What problem does it solve?

The idea of algebraic data types is not new and already exists in many other programming languages, for example:

- [Sealed classes](https://kotlinlang.org/docs/reference/sealed-classes.html) in Kotlin
- [Enumerations with associated values](https://developer.apple.com/library/content/documentation/Swift/Conceptual/Swift_Programming_Language/Enumerations.html) in Swift
- [Case classes](https://docs.scala-lang.org/tour/case-classes.html) in Scala
- [Data types](https://wiki.haskell.org/Algebraic_data_type) in Haskell

It is possible to represent such algebraic data types using subclasses: the parent class is the "enumeration"
type, and each child class represents a case of the enumeration with it's associated parameters. This will however
either require you to spread out your business logic in all the subclasses, or to cast the child class manually to
access the parameters and be very careful to only cast if you know for sure that the class is of the right type. 

The goal of DataEnum is to help you generate all these classes and give you a fluent API for easily accessing their data
in a type-safe manner.

The primary use-case we had when designing DataEnum was to execute different business logic depending on an incoming
message. And as mentioned above, we wanted to keep all that business logic in one place, and not spread it out in
different classes. With plain Java, you’d have to write something like this:

```java
if (message instanceof Login) {
    Login login = (Login) message;
    // login logic here
} else if (message instanceof Logout) {
    Logout logout = (Logout) message;
    // logout logic here
}
```

There are a number of things here that developers tend to not like: repeated if-else statements, manual `instanceof`
checks and safe-but-noisy typecasting. On top of that it doesn't look very idiomatic and there's a high risk that
mistakes get introduced over time. If you use DataEnum, you can instead write the same expression like this:

```java
message.match(
   login -> { /* login logic; the 'login' parameter is 'message' but cast to the type Login. */ },
   logout -> { /* logout logic; the 'logout' parameter is 'message' but cast to the type Logout. */ }
);
```

In this example only one of the two lambdas will be executed depending on the message type, just like with the
if-statements. `match` is just a method that takes lambdas as arguments, but if you write expressions with linebreaks
like in the example above it looks quite similar to a switch-statement, a match-expression in Scala, or a
when-expression in Kotlin. DataEnum makes use of this similarity to make match-statements look and feel like a
language construct.

There are many compelling use-cases for using an algebraic data type to represent values. To name a few:

- **Create a vocabulary of possible actions**. List all the actions that can be performed in a certain part of your 
  application, for example on a login/logout page. Each action can have different data associated with it, for example
  the login action would have a username and password, while a logout action doesn't have any data.

- **Representing states of a state machine**. This allows you to only keep the data that actually is available in each
  state, making it impossible to even reference data that isn't available in a particular state.

- **Rich, type-safe error handling** Instead of having just an error code as a result of a network request, you can
  have different types for different errors, each with relevant information attached: `ConnectivityLost`,
  `NoRouteToHost(String host)`, `TooManyRetries(int retryCount)`.

- **Metadata in RxJava streams**. It is often useful to wrap data in RxJava in order to provide metadata about what's
  happening. One common example is to represent different kinds of success and failure: `InProgress(T placeholder)`,
  `Success(T data)`, `Error(String reason)`.
 
## Status

DataEnum is in Beta status, meaning it is used in production in Spotify Android applications, but
we may keep making changes relatively quickly.

It is currently built for Java 7 (because Android doesn't support Java 8 well yet), hence the
duplication of some concepts defined in `java.util.function` (`Consumer`, `Function`, `Supplier`).

## Using it in your project

The latest version of DataEnum is available through Maven Central (LATEST_RELEASE below is 1.0.1):

#### Gradle

```
implementation 'com.spotify.dataenum:dataenum:LATEST_RELEASE'                
annotationProcessor 'com.spotify.dataenum:dataenum-processor:LATEST_RELEASE' 
```

#### Maven

```xml
<dependencies>
  <dependency>
    <groupId>com.spotify.dataenum</groupId>
    <artifactId>dataenum</artifactId>
    <version>LATEST_RELEASE</version>
  </dependency>
  <dependency>
    <groupId>com.spotify.dataenum</groupId>
    <artifactId>dataenum-processor</artifactId>
    <version>LATEST_RELEASE</version>
    <scope>optional</scope>
  </dependency>
</dependencies>
```

It may be an option to use the [annotationProcessorPaths](https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html#annotationProcessorPaths)
configuration option of the maven-compiler-plugin rather than an optional dependency.

## How do I create a DataEnum type?
First, you define all the cases and their parameters in an interface like this:

```java
@DataEnum
interface MyMessages_dataenum {
    dataenum_case Login(String userName, String password);
    dataenum_case Logout();
    dataenum_case ResetPassword(String userName);
}
```

Then, you apply the `dataenum-processor` annotation processor to that code, and your DataEnum case 
classes will be generated for you.

Some things to note:

- We use a Java interface for the specification. The rationale is that it allows the IDE to help you find
  and import types correctly. We deliberately made it look weird, so nobody would think it’s a normal class.
  This is abusing Java a bit, but we’re OK with that.

- The interface will never be used for anything other than code generation, so you should normally 
  make the interface package-private. The one exception is when one `_dataenum` spec needs to
  reference another as described below.

- The interface name has to end with `_dataenum`. This is to make the interface stick out and make
  it easier to filter out from artifacts and exclude from static analysis.

- The methods in the interface have to return a `dataenum_case`. Each method corresponds to one of the
  possible cases of the enum, and the parameters of the method becomes the parameters of that case. Note
  that the method names from the interface will be used as class names for the cases, so you'll want to
  name them using CamelCase as in the example above.

- The prefix of the `@DataEnum` annotated interface will be used as the name of a generated super-class
  (`MyMessages` in the example above). This class will have factory methods for all the cases.

- For each method in the interface, an inner class will be generated (in this example `MyMessages.Login`,
  `MyMessages.Logout` and `MyMessages.ResetPassword`). These classes will extend the outer class `MyMessages`.

## Using the generated DataEnum class
Some usage examples, based on the `@DataEnum` specification above:

```java
// Instantiate by passing in the required parameters. 
// You’ll get something that is of the super type - this is to help Java’s 
// not-always-great type inference do the right thing in many common cases.
MyMessages message = MyMessages.login("petter", "s3cr3t");

// If you actually needed the subtype you can easily cast it using the as-methods.
Logout logout = MyMessages.logout().asLogout();

// For every as-method there is also an is-method to check the type of the message.
assertThat(message.isLogin(), is(true));

// Apply different business logic to different message types. Note how getters are generated (but not
// setters, DataEnum case types should be considered immutable).
message.match(
    login -> Logger.debug("got a login request from user: {}", login.userName()),
    logout -> Logger.debug("user logged out"),
    resetPassword -> Logger.debug("password reset requested for user: {}", resetPassword.userName())
);

// So far we've been looking at 'match', but there is also the very useful 'map' which is used to
// transform values. When using 'map' you define how the message should be transformed in each case.
int passwordLength = message.map(
    login -> login.password().length(),
    logout -> 0,
    resetPassword -> -1);
}

// There are some utility methods provided that allow you to deal with unimplemented or illegal cases:
int passwordLength = message.map(
    login -> login.password().length(),
    logout -> Cases.illegal("logout message does not contain a password"), // throws IllegalStateException
    resetPassword -> Cases.todo()); // throws UnsupportedOperationException
}

// Sometimes, only a minority of cases are handled differently, in which case a 'map' or 'match'
// can lead to duplication:
int passwordLength = message.map(
    login -> handleLogin(login),
    logout -> Cases.illegal("only login is allowed"),
    resetPassword -> Cases.illegal("only login is allowed")
    // This could really get bad if there are many cases here
);

// For those scenarios you can just use regular language control structures (like if-else):
if (message.isLogin()) {
  return handleLogin(message.asLogin()); // Technically just a cast but easier to read than manual casting.
} else {
  throw new IllegalStateException("only login is allowed");
}
```

## Features

- Case types are immutable. All generated classes are value types and cannot be modified after being created. Of course
  this assumes that all the parameters of your cases are immutable too, since an object only is immutable if all its
  fields also are immutable.
- Everything is non-null by default. Passing in a null will cause an exception to be thrown unless you explicitly
  annotate the parameters as `@Nullable`. Any annotation with the name 'Nullable' can be used.
- `toString`, `hashCode`, and `equals` are generated for all case classes.
- isFoo/asFoo methods are provided, as a more high level alternative to manually doing `instanceof` and casting.
- Generic type support. The DataEnum interfaces can be type parameterized, which makes it possible to create reusable
  data types. 
- Recursive data type support. The generated DataEnum types may refer to itself recursively, even with type parameters.
  When doing so you must use the `_dataenum`-suffixed name to avoid any chicken-and-egg problems with the generated
  classes.
  
  The recursive data type support allows you to do things like this:
  
  ```java
  @DataEnum
  interface Tree_dataenum<T> {
    dataenum_case Branch(Tree_dataenum<T> left, Tree_dataenum<T> right);
    dataenum_case Leaf(T value);
  }
  ```
- Sometimes, you want to reference a dataenum from another one. You can do that using this slightly
  clunky syntax:

  ```java
  interface First_dataenum {
    dataenum_case SomeCase();
  }

  interface Second_dataenum {
    dataenum_case NeedsFirst(First_dataenum first);
  }
  ```
  The generated `NeedsFirst` class will have a member field that is of the type `First`. Again, because
  the `First` class doesn't exist until the annotation processor has run, so the `Second_dataenum` spec
  must reference the `First_dataenum` spec. If `First_dataenum` is in a different package than
  `Second_dataenum`, it must of course be public.
    

## Known weaknesses of DataEnum

- While the generated classes are immutable, they do not enforce that parameters are immutable. It is up to users of
  DataEnum to eg. use ImmutableList for lists instead of List.

- The names of the arguments to the lambdas when using `match`/`map` only indicate the type of the object by convention,
  so some discipline is required to make sure you manually update lambda argument names if a case is renamed.

- Renaming cases of a dataenum can be painful since the generated class doesn't have a connection to the interface. 

- Reordering cases can be dangerous if you only use lambdas with type-inference. If you swap the order of two cases
  with the same parameter names then usages of `map`/`match` will still compile even though they are now incorrect.
  This can be mitigated using method references instead of lambdas, lambdas with explicit type parameters, and good test
  coverage of code using DataEnum.

- The `_dataenum`-suffixed interface is only used as an input to code generation, and it breaks certain conventions
  around naming. You might need to suppress some static analysis when you use DataEnum, and you probably want to strip
  the `_dataenum` classes from artifacts.

## Alternatives
  
An alternative implementation of algebraic data types for Java is [ADT4J](https://github.com/sviperll/adt4j). We feel
DataEnum has the advantage of being less verbose than ADT4J, although ADT4J is more flexible in terms of customising
your generated types.

## Features that might be added in the future

- Generating builders for case types with many parameters.
- Generating mutator functions for case types to create modified versions of them.
- Support for writing extensions, eg. to allow adding support for serialization.
- IntelliJ plugin for refactoring and for generating map/match statements.

## Why is it called DataEnum?
The name ‘DataEnum’ comes from the fact that it’s used similarly to an enum, but you can easily and type-safely have
different data attached to each enum value.

## Code of Conduct

This project adheres to the [Open Code of Conduct][code-of-conduct]. By participating, you are expected to honor this code.

[code-of-conduct]: https://github.com/spotify/code-of-conduct/blob/master/code-of-conduct.md
