export default {
  
    name: "Payment",
  
    data () {
      return {
        total: 0
      }
    },
  
    created: function() {
      this.total = localStorage.getItem('cost')
    },
  
    methods: {
      backToMain: function() {
        this.$router.push({ name: 'ViewAndSelectItems'})
      }
    }

  }