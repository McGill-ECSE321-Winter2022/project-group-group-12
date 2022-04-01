
export default {

  data(){
    return {
      selectedScreen: 0,

      MonStartTime:'--:--',
      MonCloseTime:'--:--',
      TueStartTime:'--:--',
      TueCloseTime:'--:--',
      WedStartTime:'--:--',
      WedCloseTime:'--:--',
      ThuStartTime:'--:--',
      ThuCloseTime:'--:--',
      FriStartTime:'--:--',
      FriCloseTime:'--:--',
      SatStartTime:'--:--',
      SatCloseTime:'--:--',
      SunStartTime:'--:--',
      SunCloseTime:'--:--',
    }
  },
  methods:{
    showScreen: function(i){
      this.selectedScreen = i;
    }
  }
}
