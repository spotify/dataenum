Releasing
========

1. Checkout latest master
2. Update `README.md` so that all mentions of version name is up-to-date, commit and push update.
3. Make sure you are on a clean master and everything is pushed to upstream.
5. Run `mvn release:prepare release:perform` and follow the instructions.
6. Enter the release version when prompted or press Enter for default (Please double check the version if you do so).
7. Enter the next development version when prompted or press Enter for default (Please double check the version if you do so).
