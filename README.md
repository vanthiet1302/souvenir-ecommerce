# Bug Fix Log

## Static Resources Not Loading

**Issue**

Static resources were not loading correctly.

## Root Cause

The `HomeController` servlet was mapped to:

```
@WebServlet(urlPatterns = {"/home", "/"})
```

Mapping a servlet to `/` in Jakarta Servlet makes it act as a **catch-all handler**, intercepting all requests that are not matched earlier, including static resources.
As a result, these requests were forwarded to a JSP page instead of being served by the application server.

---

## Solution

Removed the `/` mapping from `HomeController`.

---

## Default Page Fix

After removing `/`, the application root URL produced a **404 error**.
To resolve this, a welcome page redirect was added.

### `web.xml`

```
<welcome-file>index.jsp</welcome-file>
```

### `index.jsp`

```
<%
response.sendRedirect(request.getContextPath() + "/home");
%>
```

Now accessing the root link correctly redirects to: /home```

---

