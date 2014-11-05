
# Conrad [![Stories in Ready](https://badge.waffle.io/quality-clojure/conrad.png?label=ready&title=Ready)](https://waffle.io/quality-clojure/conrad)

> "The horror! The horror!"
>
> Heart of Darkness by Joseph Conrad

Don't let your code run wild.

Conrad is a simple linting library to keep code civilized.

## Usage

The general idea is to be able to programmatically check arbitrary code according to some arbitrary standards (or your own standards, which may be just as arbitrary).

Communication to and from the library is expected to occur via [core.async].

## Known Future Plans

Perhaps you find our hodgepodge list of checks lacking? If so then we have good news! You should* able to provide your own predicate that runs over a namespace for a given granularity. This allows you to easily extend Conrad to suit your needs.

* Hopefully... someday... if you are lucky.

## License

Copyright © 2014 Chris Sims and Scott Bauer

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[core.async]: https://github.com/clojure/core.async
