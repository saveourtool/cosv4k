# Format Overview

The format is a JSON-based encoding format, using the following informal schema.
The exact details of each field are elaborated in the next section. All strings contain UTF-8 text.

```json
{
  "schema_version": "string",
  "id": "string",
  "modified": "string",
  "published": "string",
  "withdrawn": "string",
  "aliases": [ "string" ],
  "cwe_ids": [ "string" ],
  "cwe_names": [ "string" ],
  "time_line": [ {
      "type": "string",
      "value": "string"
    }
  ],
  "related": [ "string" ],
  "summary": "string",
  "details": "string",
  "severity": [ {
      "type": "string",
      "score": "string",
      "level": "string",
      "score_num": "string"
    }
  ],
  "affected": [ {
      "package": {
        "ecosystem": "string",
        "name": "string",
        "purl": "string",
        "language": "string",
        "repository": "string",
        "introduced_commits": [ "string" ],
        "fixed_commits": [ "string" ],
        "home_page:": "string",
        "edition:": "string"
      },
      "severity": [ {
          "type": "string",
          "score": "string",
          "level": "string",
          "score_num": "string"
        }
      ],
      "ranges": [ {
          "type": "string",
          "repo": "string",
          "events": [ {
              "introduced": "string",
              "fixed": "string",
              "last_affected": "string",
              "limit": "string"
            }
          ],
          "database_specific": { "see description": "" }
        }
      ],
      "versions": [ "string" ],
      "ecosystem_specific": { "see description": "" },
      "database_specific": { "see description": "" }
    }
  ],
  "patches_detail": [ {
      "patch_url": "string",
      "issue_url": "string",
      "main_language": "string",
      "author": "string",
      "commiter": "string",
      "branches": [ "string" ],
      "tags": [ "string" ]
    }
  ],
  "contributors": [ {
      "org": "string",
      "name": "string",
      "email": "string",
      "contributions": "string"
    }
  ],
  "confirm_type": "string",
  "references": [ {
      "type": "string",
      "url": "string"
    }
  ],
  "credits": [ {
      "name": "string",
      "contact": [ "string" ],
      "type": "string"
    }
  ],
  "database_specific": { "see description": "" }
}
```

# Field Details

### schema_version field

```json
{
	"schema_version": "string"
}
```

The `schema_version` field is used to indicate which version of the **COSV** schema
a particular vulnerability was exported with.
This can help consumer applications decide how to import the data for
their own systems and offer some protection against future breaking changes.
The value should be a string matching the **COSV** schema version, which follows
the [SemVer 2.0.0](https://semver.org/) format, with no leading “v” prefix. If no value is specified,
it should be assumed to be `1.0.0`, matching version `1.0` of the **COSV** schema.

### id, modified fields

```json
{
  "id": "string",
  "modified": "string"
}
```

The id field is a unique identifier for the vulnerability entry.\
It is a string of the format `<DB>-<ENTRYID>`, where `DB` names the database and `ENTRYID`
is in the format used by the database.
For example: `“OSV-2020-111”`, `“CVE-2021-3114”`, or `“GHSA-vp9c-fpxx-744v”`.

### published field

```json
{
	"published": "string"
}
```

The `published` field gives the time the entry should be considered to have been published,
as an RFC3339-formatted time stamp in UTC (ending in “Z”).

### withdrawn field

```json
{
	"withdrawn": "string"
}
```

The withdrawn field gives the time the entry should be considered to have been withdrawn,
as an RFC3339-formatted timestamp in UTC (ending in “Z”).
If the field is missing, then the entry has not been withdrawn.
Any rationale for why the vulnerability has been withdrawn should go into the summary text.

### aliases field

```json
{
	"aliases": [ "string" ]
}
```

The aliases field gives a list of IDs of the same vulnerability in other databases,
in the form of the id field.
This allows one database to claim that its own entry describes
the same vulnerability as one or more entries in other databases.

Aliases should be considered symmetric and transitive.

### cwe_ids

```json
{
	"cwe_ids": [ "string" ]
}
```

The public defect enumeration ID's corresponding to the vulnerability type;

### cwe_names

```json
{
	"cwe_names": [ "string" ]
}
```

The public defect enumeration name corresponding to the vulnerability type;

### time_line

The life cycle of the vulnerability itself,
this should be distinguished from “published” or “withdrawn”
which describes time points of this vulnerability entry not the vulnerability itself.

```json
{
	"time_line": [ {
		"type": "string",
		"value": "string"
	} ]
}
```

#### time_line[].type

The type of time point on the timeline, including but not limited to: “introduced”, “found”, “fixed”, “disclosed”; type of time point

#### time_line[].value

The value of the point in time, in line with the timestamp of UTC an RFC3339-formatted time stamp in UTC (ending in “Z”), e.g. “2023-03-13T16:43Z”

### related field

```json
{
  "related": [ "string" ]
}
```

The `related` field gives a list of IDs of closely related vulnerabilities, such as:

* A similar but completely different vulnerability.
* A similar OSV entry that bundles multiple distinct vulnerabilities in the same entry.
* Cases that do not satisfy the strict definition of `aliases`.

Related vulnerabilities are symmetric but not transitive.

### summary, details fields

```json
{
	"summary": "string",
	"details": "string"
}
```

The `summary` field gives a one-line, English textual summary of the vulnerability. 
It is recommended that this field be kept short, on the order of no more than 120 characters.

The `details` field gives additional English textual details about the vulnerability.

### severity field

```json
{
	"severity": [ {
		"type": "string",
		"score": "string",
		"level": "string",
		"score_num": "string"
	} ]
}
```

The `severity` field is a JSON array that allows generating systems
to describe the severity of a vulnerability using one or more quantitative scoring methods.
Each severity item is a JSON object specifying a type and score property, described below.

#### severity[].type field

The `severity[].type` property must be one of the types defined below,
which describes the quantitative method used to calculate the associated `score`.

* CVSS_V2 A CVSS vector string representing the unique characteristics and severity of the vulnerability using a version
  of the Common Vulnerability Scoring System notation that is == 2.0\
  `(e.g."AV:L/AC:M/Au:N/C:N/I:P/A:C")`.

* CVSS_V3 A CVSS vector string representing the unique characteristics and severity of the vulnerability using a version
  of the Common Vulnerability Scoring System notation that is >= 3.0 and < 4.0 (e.g."CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:N/A:N").

#### severity[].score field
The `severity[].score` property is a string representing the severity score based on the
selected `severity[].type`, as described above.

#### severity[].level field
Enumeration levels corresponding to the current level of danger,
such as low-risk, medium-risk, high-risk, and critical

#### severity[].score_num field

Gives the base score num, calculated based on `severity[].score`,
the value and calculation rules are defined by `severity[].type`.
e.g, for CVSS3 score, the value is between 0 and 10 with one decimal place.

### affected fields

```json
{
	"affected": [ {
		"package": {
			"ecosystem": "string",
			"name": "string",
			"purl": "string",
			"language": "string",
			"repository": "string",
			"introduced_commits": [ "string" ],
			"fixed_commits": [ "string" ],
			"home_page:": "string",
			"edition:": "string"
		},
		"severity": [ {
			"type": "string",
			"score": "string",
			"level": "string",
			"score_num": "string"
		} ],
		"ranges": [ {
			"type": "string",
			"repo": "string",
			"events": [ {
				"introduced": "string",
				"fixed": "string",
				"last_affected": "string",
				"limit": "string"
			} ]
		} ],
		"versions": [ "string" ],
		"ecosystem_specific": { "see description" : "" },
		"database_specific": { "see description" : "" }
	} ]
}
```

The `affected` field is a JSON array containing objects that describes the affected package versions,
meaning those that contain the vulnerability.

#### affected[].package field

The affected object’s package field is a JSON object identifying the affected code library or command provided by the package.
The object itself has two required fields

* `ecosystem` The ecosystem identifies the overall library ecosystem 
* `name`  The name field is a string identifying the library within its ecosystem.
* **optional** `purl` The purl field is a string following the Package URL specification that identifies the package..
* `language:`  main developing language of this package.
* `repository:`  the link of the open source repository of the package.
* `introduced_commits`  the introducing commits of this vulnerability.
* `fixed_commits`  Commit ID's; the fixing commits of this vulnerability.
* `home_page`  home page of the package.
* `edition:`  the distribution edition name of package, e.g., Alpine, Debian.

#### affected[].severity field

The severity field is an optional element defined here.
This severity field applies to a specific package,
in cases where affected packages have differing severities for the same vulnerability.
If any package level severity fields are set, the top level severity must not be set.

#### affected[].versions field

The affected object’s versions field is a JSON array of strings.
Each string is a single affected version in whatever version syntax is used by the
given package ecosystem.

#### affected[].ranges[] field

The affected object’s ranges field is a JSON array of objects describing the affected ranges of versions.

#### affected[].ranges[].type field

In the `ranges` field, the type field is required.
It specifies the type of version range being recorded and defines the interpretation
of the events object’s introduced, fixed, and any type-specific fields.

#### affected[].ranges[].events fields

The `ranges` object’s `events` field is a JSON array of objects. Each object describes a single version that either:

* Introduces a vulnerability: {"introduced": "string"}
* Fixes a vulnerability: {"fixed": "string"}
* Describes the last known affected version: {"last_affected": "string"}
* Sets an uper limit on the range being described: {"limit": "string"}

#### affected[].ranges[].repo field

The `ranges` object’s `repo` field is the URL of the package’s code repository.
The value should be in a format that’s directly usable as an argument for the version control system’s clone command (e.g. git clone).

#### affected[].ranges[].database_specific field

The `ranges` object’s `database_specific` field is a JSON object holding additional information about the range from which the record was obtained.

#### affected[].ecosystem_specific field

The `affected` object’s `ecosystem_specific` field is a JSON object holding additional information about
the vulnerability as defined by the ecosystem for which the record applies.
The meaning of the values within the object is entirely defined by the ecosystem and beyond the scope of this document.

#### affected[].database_specific field

The `affected` object’s `database_specific` field is a JSON object
holding additional information about the vulnerability as defined by the database
from which the record was obtained.
The meaning of the values within the object is entirely defined by the
database and beyond the scope of this document.


### patches_detail field

```json
{
	"patches_detail": [ {
		"patch_url": "string",
		"issue_url": "string",
		"main_language": "string",
		"author": "string",
		"commiter": "string",
		"branches": [ "string" ],
		"tags": [ "string" ]
	} ]
}
```

The detail info of the fix patch of each vulnerability

#### patches_detail[].patch_url field

The URL of fix patch

#### patches_detail[].issue_url field

The URL of the issue

#### patches_detail[].main_language field

The main code language of this vulnerability

#### patches_detail[].author field

The author of the fix commit

#### patches_detail[].commiter field

The commiter of the fix commit

#### patches_detail[].branches field

The tag names of this fixing patch.

#### patches_detail[].tags field

The tag names of this fixing patch.

### contributors field:

```json
{
	"contributors": [ {
		"org": "string",
		"name": "string",
		"email": "string",
		"contributions": "string"
	} ]
}
```

This field used to specify those persons or organizations who made contributions to this vulnerability record.

#### contributors[].org field

The organization info of each contributor

#### contributors[].name field

The name of each contributor

#### contributors[].email field

The email address of each contributor

#### contributors[].contributions field

The description of the contributions each contributor has made.


### confirm_type field

```json
{
  "confirm_type": "string"
}
```

The confirmation type of this vulnerability record,
value should be one of “manual_confirmed”, “algorithm_confirmed” or “double_confirmed”


### references field

```json
{
  "references": [ {
    "type": "string",
    "url": "string"
  } ]
}
```

The references field contains a list of JSON objects describing references.
Each object has a string field type specifying the type of reference,
and a string field url.
The url is the fully-qualified URL (including the scheme, typically “https://”)
linking to additional information, advisories, issue tracker entries,
and so on about the vulnerability itself. The type specifies what kind of reference the URL is.

### credits fields

```json
{
	"credits": [ {
		"name": "string",
		"contact": [ "string" ],
		"type": "string"
	} ]
}
```

The credits field is a JSON array providing a way to give credit for the discovery,
confirmation, patch, or other events in the life cycle of a vulnerability.

Each of the objects in the credits array must contain at minimum a `name` field specifying
the name of the individual or entity being credited, using whatever notation they prefer.
It can also optionally include a `contact` JSON array.

#### credits[].name field
`credits[].name` should specify the name, label, or other identifier of the individual or entity being credited, using whatever notation the creditor prefers.

#### credits[].contact[] field
Each `credits[].contact[]` entry should be a valid, fully qualified, plain-text URL at which the credited can be reached. Providing contacts is optional.

#### credits[].type field
The optional `credits[].type` field should specify the type or role of the individual or entity being credited.

#### database_specific field
The top-level `database_specific` field is a JSON object holding additional information about the vulnerability
as defined by the database from which the record was obtained.

Unlike the `affected[].database_specific` field which is specific to each affected product,
this top-level field allows aggregator databases to add custom data that they considered applicable to the whole vulnerability.

**Note** that this is a single field with key `database_specific`, which itself contains a JSON object with unspecified fields.