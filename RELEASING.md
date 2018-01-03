Releasing
========

1. Ensure you are set up for deploying to Maven Central.
1. Run `mvn release:prepare release:perform` and follow the instructions.
1. Enter the release version when prompted or press Enter for default (Please double check the version if you do so).
1. Enter the next development version when prompted or press Enter for default (Please double check the version if you do so).
1. Add release notes to the newly created tag on https://github.com/spotify/dataenum/releases.
1. Once the new release has propagated to Maven Central, update the `last.version` property in the
   root pom.xml to indicate the right version. Failing to do so means opening up for this:
   1. `last.version` points to version 14.
   1. version 15 is released, adding the method `foo(Integer i)`.
   1. version 16 is released, changing signature of `foo` to `foo(Boolean b)`. This is an API-breaking
      change that goes undetected, since japicmp is only checking against version 14, where `foo`
      didn't exist.
