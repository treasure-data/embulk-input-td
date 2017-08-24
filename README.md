# Treasure Data Input Plugin for Embulk

**NOTICE**: embulk-input-td v0.2.0+ only supports **Embulk v0.8.22+**.

## Overview

* **Plugin type**: input
* **Resume supported**: no
* **Cleanup supported**: yes
* **Guess supported**: no

## Configuration

- **apikey**: apikey (string, required)
- **endpoint**: hostname (string, default='api.treasuredata.com')
- **http_proxy**: http proxy configuration (tuple of host, port, useSsl, user, and password. default is null)
- **use_ssl**: the flag (boolean, default=true)
- **database**: database name (string, optional)
- **query**: presto query string (string, optional)
- **job_id**: job_id (string, optional)

## Example

```yaml
in:
  type: td
  apikey: my_apikey
  endpoint: api.treasuredata.com
  database: my_db
  query: |
    SELECT * FROM my_table
```
## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```
