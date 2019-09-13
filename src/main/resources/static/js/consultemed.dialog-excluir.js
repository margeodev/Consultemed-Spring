Consultemed = Consultemed || {};

Consultemed.DialogoExcluir = (function() {
	
	
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn')
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Excluído com sucesso!', 'success');
		}
	}
	
	function onExcluirClicado(evento) {
		
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		console.log(botaoClicado)
		var url = botaoClicado.data('url');
		
		console.log(url)
		var objeto = botaoClicado.data('objeto');
		
		swal({
			title: 'Tem certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, exclua agora!',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, url));
	
	
		Swal.fire({
			title: 'Tem certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, exclua agora!',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, url));
		
	}
	
	function onExcluirConfirmado(url) {
		console.log(url)
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso() {
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}
	
	function onErroExcluir(e) {
		console.log('ahahahah', e.responseText);
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoExcluir;
	
}());

$(function() {
	console.log('')
	var dialogo = new Consultemed.DialogoExcluir();
	dialogo.iniciar();
});

/*<a type="button" class="btn  btn-outline btn-danger" data-toggle="modal" data-target="#confirmacaoExclusaoModal"
								th:attr="data-id=${c.id} , data-nome=${c.nome}" >Excluir
	   						</a>*/

	
//	var button = $(event.) // PEGANDO O BOTAO QUE DISPARA O EVENTO
//	var nomeConatto = button.data('nome') // PEGANDO OS DADOS DO CAMPO DATA DO LINK DE EXCLUSAO - NOME
//	var idContato = button.data('id') // PEGANDO OS DADOS DO CAMPO DATA DO LINK DE EXCLUSAO - ID
//	console.log(nomeConatto)
	
//	var modal  = $(this) // PEGANDO O MODAL
//	var form   = modal.find('form'); //PEGANDO O FORM DO MODAL
//	var action = form.data('url-base'); // PEGANDO A ACTION DO FORM DO MODAL - pegando a string do atributo do form
//	
//	if(!action.endsWith('/')) {
//		action += '/';
//	}
//	form.attr('action' , action + idContato);
//	
//	modal.find('.modal-body').html('Tem certeza que deseja exlusão o contato, <strong> ' + nomeConatto + '</strong> ?')
//	
//}) 