# Format Overview

The format is a JSON-based encoding format, using the following informal schema.
The exact details of each field are elaborated in the next section. All strings contain UTF-8 text.

```json
{
	"schema_version": string,
	"id": string,
	"modified": string,
	"published": string,
	"withdrawn": string,
	"aliases": [ string ],
	"related": [ string ],
	"summary": string,
	"details": string,
	"severity": [ {
		"type": string,
		"score": string
	} ],
	"affected": [ {
		"package": {
			"ecosystem": string,
			"name": string,
			"purl": string
		},
		"severity": [ {
			"type": string,
			"score": string
		} ],
		"ranges": [ {
			"type": string,
			"repo": string,
			"events": [ {
				"introduced": string,
				"fixed": string,
				"last_affected": string,
				"limit": string
			} ],
			"database_specific": { see description }
		} ],
		"versions": [ string ],
		"ecosystem_specific": { see description },
		"database_specific": { see description }
	} ],
	"references": [ {
		"type": string,
		"url": string
	} ],
	"credits": [ {
		"name": string,
		"contact": [ string ],
		"type": string
	} ],
	"database_specific": { see description }
}
```

