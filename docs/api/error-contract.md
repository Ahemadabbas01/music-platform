# API Error Contract

Every error response from music-service has this shape:

error response shape :
{
  "timestamp": "2026-09-20T15:53:11.860189600Z",
  "status": 400,
  "error": "VALIDATION_FAILED",
  "message": "Request validation failed",
  "path": "/api/albums",
  "fieldErrors": { "name": "must not be blank" }
}

{
  "timestamp": "2026-09-20T15:53:11.860189600Z",
  "status": 404,
  "error": "RESOURCE_NOT_FOUND",
  "message": "Artist not found: 1",
  "path": "/api/artists/1",
  "fieldErrors": null
}

## Fields

| Field | Type | Description |
|---|---|---|
| timestamp | ISO-8601 UTC | When the error occurred |
| status | int | HTTP status code |
| error | string | Stable error code (see table below) |
| message | string | Human-readable message |
| path | string | Request URI that produced the error |
| fieldErrors | Map<string,string> or null | Field-level validation errors |



## Error codes

| Code | HTTP | When it occurs |
|---|---|---|
| VALIDATION_FAILED | 400 | Bean Validation rejected the request body |
| RESOURCE_NOT_FOUND | 404 | The requested resource or referenced entity does not exist |