package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Trabajador;
import control.asistencia.QRCheck.services.iterfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    private ITrabajadorService trabajadorServices;


    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Trabajador> trabajadores = trabajadorServices.buscarTodosPaginados(pageable);
        model.addAttribute("trabajadores", trabajadores);

        int totalPages = trabajadores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "trabajadores/index";
    }

    @GetMapping("/create")
    public String create(Trabajador trabajador){
        return "trabajador/create";
    }

    @PostMapping("/save")
    public String save(Trabajador trabajador, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(trabajador);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "trabajador/create";
        }

        trabajadorServices.crearOEditOne(trabajador);
        attributes.addFlashAttribute("msg", "Trabajador Registrado Correctamente");
        return "redirect:/trabajadores";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Trabajador trabajador = trabajadorServices.buscarPorId(id).get();
        model.addAttribute("trabajador", trabajador);
        return "trabajador/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Trabajador trabajador = trabajadorServices.buscarPorId(id).get();
        model.addAttribute("trabajador", trabajador);
        return "trabajador/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Trabajador trabajador = trabajadorServices.buscarPorId(id).get();
        model.addAttribute("trabajador", trabajador);
        return "trabajador/delete";
    }


    @PostMapping("/delete")
    public String delete(Trabajador trabajador, RedirectAttributes attributes){
        trabajadorServices.eliminarPorId(trabajador.getId());
        attributes.addFlashAttribute("msg", "Trabajador eliminado correctamente");
        return "redirect:/trabajador";
    }

}
