# bugzee

# Notes By Kris

_Once you generate a JHIPSTER project, how do you modify it to restrict certain entities related to a `User` onto a new page in the front-end?_

This is a common enough need in many ZipCode Spring projects.

## Generating the Code

JHIPSTER is good at generating a large amount of code on top of the Spring App engine. 
It will produce a good starter project and allows you, with the help of the entity generating machinery, to customize it to your project's domain of objects.
After the initial generation of the project, you need to create a `jdl` file which contains descriptions of the data model you'd like your app to use.

### JDL - a useful addition for working at a high level

JHIPSTER's JDL machinery can generate large amount of useful backend REST API code in the generated Spring App server for a series of Entities (the objects that you model in your app).
This project __Bugzee__, it meant to be an example of a simple Issue Tracking app, where `Tickets` are used to track issues related to some `Project`.
`Tickets` can be `ReportedBy` and `AssignedTo` a given `User`.
(Look at the [jhipster-jdl-diagram](./jhipster-jdl-diagram.png) for a visual of the Entity-Relationship Diagram (a specialized UML)).

THis diagram is a visual of the [JDL file for Bugzee](./bugzee.jdl) which describes the `domain objects` or the `business objects` used by this Issue/Ticket/Bug Tracker project.

![jhipster-jdl-diagram](./jhipster-jdl-diagram.png)

### Adding a "restricted" view of Tickets

A common milestone early in a project, is to restrict some items from the db by showing only those items "owned" by a User.
If in the JDL file you have declared a relationship between one or more of you domain objects, JHIPSTER will generate some extra code in the entity JPA repositories files to allow you to restrict a query to just the objects that are "thru" that relationship.

```jdl
relationship ManyToOne {
  Ticket{project(name)} to Project
  Ticket{assignedTo(login)} to User
  Ticket{reportedBy(login)} to User
  Comment{login} to User
}
```

This declaration, among other things, generates a handy couple of methods in the `TicketRepository` class.

```java
@Repository
public interface TicketRepository extends TicketRepositoryWithBagRelationships, JpaRepository<Ticket, Long> {
    @Query("select ticket from Ticket ticket where ticket.assignedTo.login = ?#{principal.username}")
    List<Ticket> findByAssignedToIsCurrentUser();

    @Query("select ticket from Ticket ticket where ticket.reportedBy.login = ?#{principal.username}")
    List<Ticket> findByReportedByIsCurrentUser();

    /* a WHOLE lot more...*/
```

JHIPSTER will produce a CRUD interface in the front-end that allows you access to the entities you add to the project with your JDL file.
The problem is, those pages show _all_ the Tickets (in this case Tickets are the entity which track "bugs") to a user, and while one can sort via a column, it's less than ideal.
So, we need pages which will show a User the Tickets that have been `AssignedTo` them and to show the Tickets which the User has `ReportedBy`. 
In this project, showing the User the Tickets they have been `AssignedTo` (and only those) is the first task in this. 
The second is to mimic the first set of changes to show the user only the Tickets which have been `ReportedBy` them.

Modify the test data records to have a couple bugs which are assigned and reported by the `User` ADMIN and a few assigned and reported by `User` USER.

I did this twice.
The first one `listuser` was a copy of the `code/src/main/webapp/app/entities/ticket/list` component. 
Yes, you could generate this component from scratch. 
I chose to do it by copying a working set of files, and them modifying them so that I could leverage the "spidery" connections you find in these Angular/React frontends.
It turns out there are a bunch of files you have to edit to "wire" it all into the project.
It gives me a subset of `Tickets` restricted by the `AssignedTo` relationship.

The second one was the `listreport` set of changes, giving me a `ReportedBy` subset of `Tickets`.

### Adding a AssignedTo Tickets `listuser` (assignedto) page.

- copied `list/` folder of components to `listuser/`.
- change all `TicketComponent` to `TickerUserComponent`
- added the API call in the back end to  `TicketResource` by findByAssignedToIsCurrentUser
- added to `TicketService` `queryassign(req?: any): Observable<EntityArrayResponseType>`
- added a link to the Home component
- modified `code/src/main/webapp/app/entities/ticket/ticket.module.ts` adding TicketUserComponent
- added to `code/src/main/webapp/app/entities/ticket/route/ticket-routing.module.ts` items for `assign` option.

### Adding a ReportedBy Tickets `listreport` page.

- copied `list/` folder of components to `listreported/`.
- change all `TicketComponent` to `TicketReportComponent`
- added the API call in the back end to  `TicketResource` by findByReportedByIsCurrentUser
- added to `TicketService` `queryreport(req?: any): Observable<EntityArrayResponseType>`
- added a link to the Home component
- modified `code/src/main/webapp/app/entities/ticket/ticket.module.ts` adding TicketReportComponent
- added to `code/src/main/webapp/app/entities/ticket/route/ticket-routing.module.ts` items for `report` option.

These two activities leave the project with two added frontend URLs:

- `/ticket/assign` shows the `Tickets` that are `assignedTo` the current logged in User.
- `/ticket/report` shows the `Tickets` that are `reportedTo` the current logged in User.

I then placed these on the `Main Component` the "main page" of the app. See `code/src/main/webapp/app/home/home.component.html` (and notice how they are placed so that you only see them when you are logged in as a user.)


# The Original README.md 

This is the original readme from the generated project files.

This application was generated using JHipster 7.8.1, you can find documentation and help at [https://www.jhipster.tech](https://www.jhipster.tech).

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husk, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if ommited) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files

- `npmw` - wrapper to use locally installed npm.
  JHipster installs Node and npm locally using the build tool by default. This wrapper makes sure npm is installed locally and uses it avoiding some differences different versions can cause. By using `./npmw` instead of the traditional `npm` you can configure a Node-less environment to develop or test your application.
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use npm scripts and [Angular CLI][] with [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### PWA Support

JHipster ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is disabled by default. To enable it, uncomment the following code in `src/main/webapp/app/app.module.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```
npm install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```
npm install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/app.module.ts](src/main/webapp/app/app.module.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

```
ng generate component my-component
```

will generate few files:

```
create src/main/webapp/app/my-component/my-component.component.html
create src/main/webapp/app/my-component/my-component.component.ts
update src/main/webapp/app/app.module.ts
```

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker-compose -f src/main/docker/jhipster-control-center.yml up
```

## Building for production

### Packaging as jar

To build the final jar and optimize the bugzee application for production, run:

```
./mvnw -Pprod clean verify
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

### Client tests

Unit tests are run by [Jest][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
npm test
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.8.1 archive]: https://www.jhipster.tech
[using jhipster in development]: https://www.jhipster.tech/development/
[using docker and docker-compose]: https://www.jhipster.tech/docker-compose
[using jhipster in production]: https://www.jhipster.tech/production/
[running tests page]: https://www.jhipster.tech/running-tests/
[code quality page]: https://www.jhipster.tech/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/setting-up-ci/
[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/
[webpack]: https://webpack.github.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
[angular cli]: https://cli.angular.io/
