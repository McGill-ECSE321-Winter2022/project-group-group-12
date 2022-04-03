<template>
  <div class="hello">
    <h1 style="text-align:center;font-size:50px;"> <p style="color:powderblue;"> {{ msg }} </p> </h1>
    <h1 style="text-align:center;font-size:30px;"> <p style="color:#FFD700;"> {{ quote }} </p> </h1>
    <center><img src="../assets/Logo_GSSS.png" alt="centered image" height="300" width="300"> </center>
    <br>
    <h2>Available Links</h2>
    <ul>
      <li><a href="/customer/shop"> Main shopping page</a></li>
      <li><a href="/owner/purchases" > Owner ViewPurchases </a></li>
      <li><a href="/employee/purchases" > Employee ViewPurchases </a></li>
      <li><a href="/owner/viewandedititems" > Owner view and edit items </a></li>
      <li><a href="/login"  > Login Page</a></li>
      <li><a href="/signup"  > Signup Page</a></li>
      <li><a href="/employee/customers"  > Customer List</a></li>
      <li><a href="/employee/storeinformation" >Store Information employee</a></li>
      <li><a href="/owner/systeminformation"  > System Information</a></li>
      <li><a href="/customer/payment" >Payment page</a></li>
      <li><a href="/customer/account" >Customer Account page</a></li>

      <li><a href="/customer/storeinformation" >Store Information customer</a></li>
      <li><a href="/customer/orderhistory" >Order History</a></li>
      <li><a href="/owner/shifts" >Owner shifts view page</a></li>


    </ul>
    <div style="margin: 4rem 0;">
      <h2>!!! The following is intended for development purposes only !!!</h2>
      <p>Choose a permission:</p>
      <button v-on:click="changePermission('')" v-bind:style="!permission ? 'background: blue;' : ''">None</button>
      <button v-on:click="changePermission('Customer')" v-bind:style="permission == 'Customer' ? 'background: blue;' : ''">Customer</button>
      <button v-on:click="changePermission('Employee')" v-bind:style="permission == 'Employee' ? 'background: blue;' : ''">Employee</button>
      <button v-on:click="changePermission('Owner')" v-bind:style="permission == 'Owner' ? 'background: blue;' : ''">Owner</button>
    </div>
    <h2>Github</h2>
    <li><a href="https://github.com/McGill-ECSE321-Winter2022/project-group-group-12" target="_blank">Our github page</a></li>
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome back to work, GSSS staff members !',
      quote: '',
      count: 0,
      permission: localStorage.permission,
      quoteBank: [
        '\"Life isn\'t about finding yourself, it is about creating yourself.\"\n\n-George Bernard Shaw',
        '\"If you are going down the right path and you are willing to keep walking, eventually you\'ll make progress.\"\n-Barrack Obama',
        '\"I have far more respect for the person with a single idea who gets there than one with a thousand ideas and does nothing.\"\n-Thomas Edison',
        '\"What is necessary to change a person is to change awareness of himself.\"\n\n-Anonymous',
        '\"Many people die at the age of 25, but their bodies are not buried until 75.\"\n\n-Benjamin Franklin',
        '\"If you don\'t get up every morning with a burning desire to do things, you don\'t have enough goals.\"\n-Lou Holtz',
        '\"The way to learning is to get rid of the arrogance of knowledge.\"\n-Confucius',
        '\"One can have no smaller or greater master than the mastery of oneself.\"\n-Leonardo Da Vinci',
        '\"Empty pockets never held anyone back. Only empty heads and hearts can do that.\"\n-Norman Vincent Peale',
        '\"Great things are not done by impulse, but a series of small things brought together.\"\n-Vincent Van Gogh',
        '\"Why measure your success by the suggestions of society when you can become a success on your own terms.\"\n-Robin Sharma',
        '\"A man may imagine things that are false, but he can only undetsand things that are true.\"\n-Isaac Newton',
        '\"Our fatigue is often caused not by work, but by worry, frustration and resentment.\"\n-Dale Carnegie',
        '\"True freedom is impossible without a mind made free by discipline.\"\n-Mortimer Adler',
        '\"Focus is not saying yes to all important things, rather it is saying no to less important things.\"\n-Steve Jobs',
        '\"Discipline is the bridge between goals and accomplishment.\"\n-Jim Rohn',
        '\"Success in life comes when you simply refuse to give up, with goals so strong that obstacles, failure and loss only act as motivation.\"\n-Jim Rohn',
        '\"Be yourself. Everyone else is already taken.\"\n-Oscar Wilde',
        '\"Be who you are and say what you feel, because those who mind don\'t matter and those who matter don\'t mind.\"\n-Bernard M. Baruch',
        '\"Imperfection is beauty, madness is genius and it\'s better to be absolutely ridiculous than absolutely boring.\"\n-Marilyn Monroe',
        '\"When you are content to be simply yourself and don\'t compare or compete, everyone will respect you.\"\n-Lao Tzu',
        '\"If you are always trying to be ordinary, you will never know how extraordinary you can be.\"\n-Michelangelo',
        '\"Leadership is solving problems. The day people stop bringing you their problems is the day you have stopped leading them.\"\n-Colin Powell',
        '\"Start by doing what\'s necessary; then do what\'s possible and suddenly you are doing the impossible.\"\n-Francis of Assisi',
        '\"It is our choices that show what we truly are, far more than our abilities.\"\n-Albus Dumbledore',
        '\"It does not do to dwell on dreams and forget to live.\"\n-Mirror of Erised'
    ]
    }
  },

  created: function() {
    // Choosing a quote randomly
    this.quote = this.quoteBank[Math.floor(Math.random() * this.quoteBank.length)]
  },

  methods: {
    changePermission: function(p){
      localStorage.setItem("permission", p);
      this.$router.go();
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
