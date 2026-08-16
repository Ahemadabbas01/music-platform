# ADR-002: Evolving Toward Microservices

## Status

Accepted

## Context
Why might someone want to use microservices for our music platform?

Microservices aren't simply dividing a system into independent pieces. The important part is that each service represents a meaningful business capability and has clear ownership.

Music Service
    → music catalog

Auth Service
    → identity/authentication

Playlist Service
    → playlists
## Problem
What problems could we create if we immediately started with:
Eureka
Gateway
Auth Service
Music Service
Playlist Service
History Service
Kafka
Redis ??

without requirement how we use Redis if we not created CRUD operation

If we immediately create:

Gateway
Eureka
Auth
Music
Playlist
History
Kafka
Redis

we introduce many moving parts before validating the core domain.

For example, a simple song request could eventually involve:

Angular
  ↓
Gateway
  ↓
Eureka
  ↓
Music Service
  ↓
PostgreSQL

Instead of first learning:

Client
  ↓
Music Service
  ↓
PostgreSQL
## Options Considered

Option A : What would be the advantages and disadvantages of starting directly with microservices?
unnecessary services who no need and make project more complex
advantages:

Independent deployment
Independent scaling
Clear service ownership
Fault isolation
Technology/service independence

disadvantage  :

More services to develop
More configuration
More network communication
More failure points
More infrastructure
More difficult debugging
More deployment complexity
Harder local development
Architectural decisions made before understanding the domain

Option B : What would be the advantages and disadvantages of starting with a simpler application first?

We want to establish working business functionality first and introduce distributed architecture when there is a reason to do so.
The disadvantage is that we may need refactoring when we later extract services.

## Decision
Which approach do you recommend for our project?
Start with a simpler modular application and evolve toward microservices when actual requirements justify the additional complexity.

## Reason

Learning objective
      +
Core functionality
      +
Avoid premature complexity
      +
Understand domain first
      ↓
Start simple

## Consequences
What do you think we'll gain, and what will we sacrifice, by choosing your approach?

We delay learning and implementing distributed-system concerns until later phases.
The system may require refactoring when services are extracted.

We must maintain clear module boundaries from the beginning so that future service extraction is easier.