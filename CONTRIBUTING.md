# AfnwCore2 - Contribution Guidelines

## Setup

**Usage: Git・IntelliJ IDEA**

1. Fork & Clone the repository
2. Open the project
3. Auto build gradle project
4. Checkout branch! (but, save the branch name rule!)
5. Code your heart out!
6. Commit & Push
7. [Submit a pull request](https://github.com/AfnwTeam/AfnwCore2/compare)

## Coating Terms and Conditions

1. The first letter of the class name must be capitalized.
2. Do not nest if statements too deeply. (if possible)

**But:**

```java
public class FirstPlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) {
            p.sendMessage("Welcome back!");
        }
    }
}
```

**Good:**

```java
public class FirstPlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPlayedBefore()) return;
        p.sendMessage("Welcome back!");
    }
}
```

3. Be as brief as possible when writing comments.

## Conventional Commit and Semantic pull request

Please follow [**Conventional Commit**](https://conventionalcommits.org/ja/) when writing commit messages and pull requests.

> The Conventional Commits specification is a lightweight convention for commit messages. It provides simple rules for creating an explicit commit history. Following these rules simplifies the deployment of automation tools. By explaining feature additions, modifications, destructive changes, etc. in commit messages, this convention works cooperatively with SemVer.
> Citing [Conventional Commits](https://conventionalcommits.org/ja/)

- The commit message should be in the following form:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

- The name of the pull request should be in the following form:

```
<type>[optional scope]: <description>
```

The `<type>` must specify each of the types listed below to convey the intent to the user or developer.

| Type       | Element                                                                                              |
|------------|------------------------------------------------------------------------------------------------------|
| `fix`      | Commits with this type patch bugs in the codebase.                                                   | `fix` | `fix
| `feat`     | Commits with this type add new functionality to the codebase.                                        | `feat` | Commits with this type add new functionality to the codebase.
| `build`    | Commits with this type make changes to the part of the program that compiles and builds the program. | `build` | Commits with this type change the parts of the program that compile and build.
| `ci`       | Commits with this type make changes related to GitHub Actions.                                       | `ci` | Commits with this type make changes related to GitHub Actions.
| `docs`     | Commits with this type make changes to documentation, etc.                                           | `docs` | Commits with this type make changes to documents, etc.
| `refactor` | Commits with this type perform code base refactoring.                                                | `refactor` | Commits with this type perform code-based refactoring.
| `chore`    | Commits with this type are used for file organization, dependency updates, etc.                      | `chore` | Commits with this type do things like file cleanup, dependency updates, etc.

`BREAKING CHANGE` in the footer or immediately after the type/scope `! ` is added immediately after the type/scope, it indicates a destructive change in the specification. (Equivalent to `MAJOR` in Semantic Versioning.)

The `BREAKING CHANGE` can also be included in commits of any type.


