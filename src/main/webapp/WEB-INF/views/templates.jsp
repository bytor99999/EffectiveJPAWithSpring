<!-- Template for Guest List on Event Details Page-->
<script id="personList" type=text/html>
    <h1 class="typeMargin">{{type}}</h1>
    <span class="id headerTitles"><h2>ID</h2></span>
    <span class="firstName headerTitles"><h2>First Name</h2></span>
    <span class="lastName headerTitles"><h2>Last Name</h2></span>
    <ul>
        {{#domainObjects}}
            <li class="line"><b>{{>personPartial}}</b>{{>addressList}}</li>
        {{/domainObjects}}
    </ul>
 </script>

<script id="personPartial" class="partial" type="text/html">
    <section class="gapAboveAndBelow">
        <span class="id">{{id}}</span>
        <span class="firstName">{{firstName}}</span>
        <span class="lastName">{{lastName}}</span>
    </section>
</script>

<script id="addressList" type=text/html>
    <div class="indentMe5">
        <h2>Addresses:</h2>
        <span class="street1 headerTitles">Street 1</span>
        <span class="street2 headerTitles">Street 2</span>
        <span class="city headerTitles">City</span>
        <span class="state headerTitles">State</span>
        <span class="zip headerTitles">Zip Code</span>
        <ul>
            {{#addresses}}
                <li class="halfline">{{>addressPartial}}</li>
            {{/addresses}}
        </ul>
    </div>
 </script>

<script id="addressPartial" class="partial" type="text/html">
    <div class="textAlignLeft">
        <span class="street1">{{street1}}</span>
        <span class="street2">{{street2}}</span>
        <span class="city">{{city}}</span>
        <span class="state">{{state}}</span>
        <span class="zip">{{zipCode}}</span>
    </div>
</script>